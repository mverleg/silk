package nl.markv.silk.parse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.sun.tools.javac.util.Pair;
import nl.markv.silk.flatdag.Flattener;
import nl.markv.silk.pojos.v0_2_0.LongColumn;
import nl.markv.silk.types.Column;
import nl.markv.silk.types.DatabaseSpecific;
import nl.markv.silk.types.Db;
import nl.markv.silk.types.SilkSchema;
import nl.markv.silk.types.Table;

import static org.apache.commons.lang3.Validate.isTrue;

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
			for (LongColumn pojoColumn : pojoTable.columns) {
				Column richColumn = convertMinimalColumn(richTable, pojoColumn);
			}

			// Then create interconnections.
		}



		// Now that tables are done, do the database-level metadata.
		if (pojoSchema.db != null) {
			return SilkSchema.db(schemaName, pojoSchema.silk, convertDb(pojoSchema.db));
		} else {
			isTrue(tables.size() == 1);
			return SilkSchema.table(schemaName, pojoSchema.silk, tables.get(0));
		}
	}

	@Nonnull
	private Table convertMinimalTable(nl.markv.silk.pojos.v0_2_0.Table table) {
		Table richTable = new Table(
				null,  // database set later
				table.name,
				table.group,
				table.description,
				//TODO @mark:
				new ArrayList<>(),  // columns added later
				//TODO @mark:
				new ArrayList<>(),  // primaryKey set later
				//TODO @mark:
				null,  // references set later
				//TODO @mark:
				null,  // uniqueConstraints set later
				//TODO @mark:
				null,  // checkConstraints set later
				convertDbSpecific(table.databaseSpecific)
		);
		isTrue(tables.containsKey(table.name), "table " + table.name + " not unique in database " +
				"(note that at this time, it must be unique across all groups)");
		tables.put(table.name, richTable);
		return richTable;
	}

	@Nonnull
	private Column convertMinimalColumn(@Nonnull Table richTable, @Nonnull LongColumn pojoColumn) {
		Column richColumn = new Column(
				richTable,
				pojoColumn.name,
				//TODO @mark:
				pojoColumn.type,
				pojoColumn.nullable,
				pojoColumn.defaultValue,
				convertAutoValue(pojoColumn.autoValue)
		);
		richTable.columns.add(richColumn);
		Pair<String, String> columnIdentifier = Pair.of(richTable.name, richColumn.name);
		isTrue(!columns.containsKey(columnIdentifier), "column " + richColumn.name + " not unique in table " + richTable.name);
		columns.put(columnIdentifier, richColumn);
		return richColumn;
	}

	@Nullable
	private Column.AutoOptions convertAutoValue(@Nullable LongColumn.AutoOptions autoValue) {
		if (autoValue == null) {
			return null;
		}
		return Column.AutoOptions.fromValue(autoValue.value());
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
	private DatabaseSpecific convertDbSpecific(@Nonnull nl.markv.silk.pojos.v0_2_0.DatabaseSpecific databaseSpecific) {
		return new DatabaseSpecific(
				null,
				new HashMap<>(databaseSpecific.getAdditionalProperties())
		);
	}
}
