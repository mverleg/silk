package nl.markv.silk.parse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.lang3.tuple.Pair;

import nl.markv.silk.flatdag.Flattener;
import nl.markv.silk.types.CheckConstraint;
import nl.markv.silk.types.Column;
import nl.markv.silk.types.ColumnMapping;
import nl.markv.silk.types.DatabaseSpecific;
import nl.markv.silk.types.Db;
import nl.markv.silk.types.ForeignKey;
import nl.markv.silk.types.SilkSchema;
import nl.markv.silk.types.Table;
import nl.markv.silk.types.UniqueConstraint;

import static nl.markv.silk.types.DataType.fromSilkString;
import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notNull;

/**
 * Convert pojo objects to a more stable representation that has more metadata.
 */
public class Enricher {
	//TODO @mark: link pojos everywhere

	private Map<String, Table> tables;
	private Map<Pair<String, String>, Column> columns;

	private void reset() {
		tables = new HashMap<>();
		columns = new HashMap<>();
	}

	@Nonnull
	@CheckReturnValue
	public SilkSchema enrich(@Nonnull String schemaName, @Nonnull nl.markv.silk.pojos.v0_2_0.SilkSchema pojoSchema) {
		reset();

		// Do all the table and lower level processing first.
		Collection<nl.markv.silk.pojos.v0_2_0.Table> pojoTables = pojoSchema.db != null
				? pojoSchema.db.tables : Collections.singleton(pojoSchema.table);
		for (nl.markv.silk.pojos.v0_2_0.Table pojoTable : pojoTables) {

			// First convert all the tables.
			Table richTable = convertMinimalTable(pojoTable);

			// Add all the columns to them.
			for (nl.markv.silk.pojos.v0_2_0.LongColumn pojoColumn : pojoTable.columns) {
				convertMinimalColumn(richTable, pojoColumn);
			}
		}

		// Now other entities can refer to their target objects.
		for (nl.markv.silk.pojos.v0_2_0.Table pojoTable : pojoTables) {
			Table richTable = notNull(tables.get(tableIdentifier(pojoTable.name)));
			convertPrimaryKey(richTable, pojoTable.primaryKey);
			if (pojoTable.references != null) {
				for (nl.markv.silk.pojos.v0_2_0.ForeignKey pojoReference : pojoTable.references) {
					convertReference(richTable, pojoReference);
				}
			}
			if (pojoTable.uniqueConstraints != null) {
				for (nl.markv.silk.pojos.v0_2_0.UniqueConstraint pojoUniqueConstraint : pojoTable.uniqueConstraints) {
					convertUniqueConstraint(richTable, pojoUniqueConstraint);
				}
			}
			if (pojoTable.checkConstraints != null) {
				for (nl.markv.silk.pojos.v0_2_0.CheckConstraint pojoCheckConstraint : pojoTable.checkConstraints) {
					convertCheckConstraint(richTable, pojoCheckConstraint);
				}
			}
		}

		// Now that tables are done, do the database-level metadata.
		if (pojoSchema.db != null) {
			return SilkSchema.db(schemaName, pojoSchema.silk, convertDb(pojoSchema.db));
		} else {
			isTrue(tables.size() == 1);
			return SilkSchema.table(schemaName, pojoSchema.silk, tables.values().iterator().next());
		}
	}

	@Nonnull
	private Table convertMinimalTable(nl.markv.silk.pojos.v0_2_0.Table table) {
		Table richTable = new Table(
				null,  // database set later
				table.name,
				table.group,
				table.description,
				new ArrayList<>(),  // columns added later
				new ArrayList<>(),  // primaryKey set later
				null,  // references set later
				null,  // uniqueConstraints set later
				null,  // checkConstraints set later
				convertDbSpecific(table.databaseSpecific)
		);
		isTrue(!tables.containsKey(tableIdentifier(table.name)), "table '" + table.name + "' not unique in database " +
				"(note that at this time, it must be unique across all groups)");
		tables.put(tableIdentifier(table.name), richTable);
		return richTable;
	}

	@Nonnull
	private Column convertMinimalColumn(@Nonnull Table richTable, @Nonnull nl.markv.silk.pojos.v0_2_0.LongColumn pojoColumn) {
		Column richColumn = new Column(
				richTable,
				pojoColumn.name,
				fromSilkString(pojoColumn.type),
				pojoColumn.nullable,
				pojoColumn.defaultValue,
				convertAutoValue(pojoColumn.autoValue)
		);
		richTable.columns.add(richColumn);
		Pair<String, String> columnIdentifier = columnIdentifier(richTable, richColumn.name);
		isTrue(!columns.containsKey(columnIdentifier), "column '" + richColumn.name + "' not unique in table '" + richTable.name +
				"' (note that at this time, this is case-insensitive, so 'column_a' equals 'Column_A')");
		columns.put(columnIdentifier, richColumn);
		return richColumn;
	}

	@Nullable
	private Column.AutoOptions convertAutoValue(@Nullable nl.markv.silk.pojos.v0_2_0.LongColumn.AutoOptions autoValue) {
		if (autoValue == null) {
			return null;
		}
		return Column.AutoOptions.fromValue(autoValue.value());
	}

	private void convertPrimaryKey(Table richTable, List<String> pojoPrimaryKey) {
		richTable.primaryKey = pojoPrimaryKey.stream()
				.map(colName -> columnIdentifier(richTable, colName))
				.map(colIden -> notNull(columns.get(colIden),
						"Primary key refers to column '" + colIden.getRight() + "' in table '" +
						colIden.getLeft() + "' but the column does not exist"))
				.collect(Collectors.toList());
		richTable.primaryKeyNames = richTable.primaryKey.stream()
				.map(c -> c.name)
				.collect(Collectors.toList());
	}

	private void convertReference(Table richSourceTable, nl.markv.silk.pojos.v0_2_0.ForeignKey pojoForeignKey) {
		Table targetTable = notNull(tables.get(tableIdentifier(pojoForeignKey.targetTable)),
				"Reference from '" + richSourceTable.name + "' to '" + pojoForeignKey.targetTable +
				"' but the target table ('" + pojoForeignKey.targetTable + "') was not found");
		List<ColumnMapping> columnMappings = new ArrayList<>();
		for (nl.markv.silk.pojos.v0_2_0.ColumnMapping pojoMap : pojoForeignKey.columns) {
			String name = "Reference from '" + richSourceTable.name + "." + pojoMap.from +
					"' to '" + targetTable.name + "." + pojoMap.to + "'";
			Column fromCol = notNull(columns.get(columnIdentifier(richSourceTable, pojoMap.from)),
					name + " failed because from-column '" + richSourceTable.name + "." + pojoMap.from + "' does not exist");
			Column toCol = notNull(columns.get(columnIdentifier(targetTable, pojoMap.to)),
					name + " failed because to-column '" + targetTable.name + "." + pojoMap.to + "' does not exist");
			ColumnMapping richMap = new ColumnMapping(
					null,  // foreignKey filled later
					fromCol,
					toCol
			);
			columnMappings.add(richMap);
		}
		ForeignKey richForeignKey = new ForeignKey(
				richSourceTable,
				pojoForeignKey.name,
				targetTable,
				columnMappings
		);
		columnMappings.forEach(map -> map.foreignKey = richForeignKey);
		richSourceTable.references.add(richForeignKey);
		targetTable.incomingReferences.add(richForeignKey);
	}

	private void convertUniqueConstraint(Table richTable, nl.markv.silk.pojos.v0_2_0.UniqueConstraint pojoUniqueConstraint) {
		UniqueConstraint richUniqueConstraint = new UniqueConstraint(
				richTable,
				pojoUniqueConstraint.name,
				pojoUniqueConstraint.columns.stream()
						.map(colName -> notNull(columns.get(columnIdentifier(richTable, colName)),
								"Unique constraint on table '" + richTable.name +
								"' includes non-existent column '" + colName + "'"))
						.collect(Collectors.toList())
		);
		richTable.uniqueConstraints.add(richUniqueConstraint);
	}

	private void convertCheckConstraint(Table richTable, nl.markv.silk.pojos.v0_2_0.CheckConstraint pojoCheckConstraint) {
		CheckConstraint richCheckConstraint = new CheckConstraint(
				richTable,
				pojoCheckConstraint.name,
				pojoCheckConstraint.condition
		);
		richTable.checkConstraints.add(richCheckConstraint);
	}

	@Nonnull
	private Db convertDb(@Nonnull nl.markv.silk.pojos.v0_2_0.Db db) {
		List<Table> dbTables = Flattener.dependencyOrder(tables.values().iterator());
		Db richDb = new Db(
				db.name,
				db.description,
				dbTables,
				db.databaseType,
				convertDbSpecific(db.databaseSpecific)
		);
		dbTables.forEach(tab -> tab.database = richDb);
		return richDb;
	}

	@Nonnull
	private String tableIdentifier(@Nonnull String tableName) {
		return tableName.toLowerCase();
	}

	@Nonnull
	private Pair<String, String> columnIdentifier(@Nonnull Table table, @Nonnull String columnName) {
		return Pair.of(table.name.toLowerCase(), columnName.toLowerCase());
	}

	@Nullable
	private DatabaseSpecific convertDbSpecific(@Nullable nl.markv.silk.pojos.v0_2_0.DatabaseSpecific databaseSpecific) {
		if (databaseSpecific == null) {
			return null;
		}
		return new DatabaseSpecific(
				null,
				new HashMap<>(databaseSpecific.getAdditionalProperties())
		);
	}
}
