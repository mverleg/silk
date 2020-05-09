package nl.markv.silk.parse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

import nl.markv.silk.types.CheckConstraint;
import nl.markv.silk.types.Column;
import nl.markv.silk.types.DatabaseSpecific;
import nl.markv.silk.types.Db;
import nl.markv.silk.types.ForeignKey;
import nl.markv.silk.types.SilkSchema;
import nl.markv.silk.types.Table;
import nl.markv.silk.types.UniqueConstraint;

import static org.apache.commons.lang3.Validate.isTrue;

/**
 * Convert pojo objects to a more stable representation that has more metadata.
 */
public class Enricher {

	private List<CheckConstraint> checkConstraints;
	private List<Column> columns;
	private List<UniqueConstraint> uniqueConstraints;
	private List<ForeignKey> foreignKeys;
	private List<Table> tables;

	private void reset() {
		checkConstraints = new ArrayList<>();
		columns = new ArrayList<>();
		uniqueConstraints = new ArrayList<>();
		foreignKeys = new ArrayList<>();
		tables = new ArrayList<>();
	}

	@Nonnull
	@CheckReturnValue
	public SilkSchema enrich(@Nonnull String schemaName, @Nonnull nl.markv.silk.pojos.v0_2_0.SilkSchema pojoSchema) {
		reset();

		// Do all the table and lower level processing first.
		Collection<nl.markv.silk.pojos.v0_2_0.Table> pojoTables = pojoSchema.db != null
				? pojoSchema.db.tables : Collections.singleton(pojoSchema.table);
		for (nl.markv.silk.pojos.v0_2_0.Table table : pojoTables) {

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
	private Db convertDb(@Nonnull nl.markv.silk.pojos.v0_2_0.Db db) {
		return new Db(
				db.name,
				db.description,
				tables,
				db.databaseType,
				convertDbSpecific(db.databaseSpecific)
		);
	}

	@Nonnull
	private DatabaseSpecific convertDbSpecific(@Nonnull nl.markv.silk.pojos.v0_2_0.DatabaseSpecific databaseSpecific) {
		return new DatabaseSpecific(
				null,
				new HashMap<>(databaseSpecific.getAdditionalProperties())
		);
	}
}
