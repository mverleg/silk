package nl.markv.silk.parse;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

import nl.markv.silk.flatdag.Flattener;
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

	private Map<String, Table> tables;
	private Map<String, Column> columns;

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
		for (nl.markv.silk.pojos.v0_2_0.Table table : pojoTables) {

			// First convert all the tables.
			convertMinimalTable();

			// Add all the columns to them.


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
		return new Table(
				null,
				table.name,
				table.group,
				table.description,
				table.columns,
				table.primaryKey,
				table.references,
				table.uniqueConstraints,
				table.checkConstraints,
				table.databaseSpecific
		);
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
