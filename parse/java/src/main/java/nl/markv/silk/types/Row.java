package nl.markv.silk.types;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.lang3.tuple.Pair;

/**
 * A data row in a table, used for iteration.
 */
public class Row {

	@Nonnull
	public final Table table;
	public final int rowNr;

	public Row(@Nonnull Table table, int rowNr) {
		assert rowNr < table.data.size();
		this.table = table;
		this.rowNr = rowNr;
	}

	/**
	 * Returns a stream of the cells that have a provided value.
	 *
	 * Columns with defaults may not have a value. Per table, a value will be present for
	 * either none of the rows, or all of the rows.
	 */
	@Nonnull
	public Stream<Object> cells() {
		return table.columns.stream()
				.map(col -> table.data.generic.get(col.nameLowercase))
				.filter(row -> row != null)
				.map(row -> row[rowNr]);
	}

	@Nonnull
	public Stream<Object> cells(@Nonnull BiFunction<Column, Object, Object> map) {
		return table.columns.stream()
				.map(col -> Pair.of(col, table.data.generic.get(col.nameLowercase)))
				.filter(col_val -> col_val.getRight() != null)
				.map(col_val -> Pair.of(col_val.getLeft(), col_val.getRight()[rowNr]))
				.map(col_val -> map.apply(col_val.getLeft(), col_val.getRight()));
	}

	@Nullable
	public Object anyTypeForColumn(@Nonnull String columnNameLowercase) {
		assert table.data.strings.containsKey(columnNameLowercase):
				"Table '" + table.name + "' has no column '" + columnNameLowercase + "'";
		return table.data.generic.get(columnNameLowercase)[rowNr];
	}

	@Nullable
	public Object anyTypeForColumn(@Nonnull Column column) {
		return anyTypeForColumn(column.nameLowercase);
	}

	@Nullable
	public String stringForColumn(@Nonnull String columnNameLowercase) {
		assert table.data.strings.containsKey(columnNameLowercase):
				"Table '" + table.name + "' has no string column '" + columnNameLowercase + "'";
		return table.data.strings.get(columnNameLowercase)[rowNr];
	}

	@Nullable
	public String stringForColumn(@Nonnull Column column) {
		return stringForColumn(column.nameLowercase);
	}

	@Nullable
	public ZonedDateTime dateForColumn(@Nonnull String columnNameLowercase) {
		assert table.data.dates.containsKey(columnNameLowercase):
				"Table '" + table.name + "' has no date column '" + columnNameLowercase + "'";
		return table.data.dates.get(columnNameLowercase)[rowNr];
	}

	@Nullable
	public ZonedDateTime dateForColumn(@Nonnull Column column) {
		return dateForColumn(column.nameLowercase);
	}

	@Nullable
	public BigDecimal decimalForColumn(@Nonnull String columnNameLowercase) {
		assert table.data.decimals.containsKey(columnNameLowercase):
				"Table '" + table.name + "' has no decimal column '" + columnNameLowercase + "'";
		return table.data.decimals.get(columnNameLowercase)[rowNr];
	}

	@Nullable
	public BigDecimal decimalForColumn(@Nonnull Column column) {
		return decimalForColumn(column.nameLowercase);
	}

	@Nullable
	public Integer integerForColumn(@Nonnull String columnNameLowercase) {
		assert table.data.integers.containsKey(columnNameLowercase):
				"Table '" + table.name + "' has no integer column '" + columnNameLowercase + "'";
		return table.data.integers.get(columnNameLowercase)[rowNr];
	}

	@Nullable
	public Integer integerForColumn(@Nonnull Column column) {
		return integerForColumn(column.nameLowercase);
	}
}
