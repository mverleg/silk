package nl.markv.silk.types;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

public class Data {
	// Mapping from lowercase name to a column of data.

	@Nonnull
	final Table table;

	// Map to columns of data (as Objects, because the type may differ).
	@Nonnull
	public Map<String, Object[]> generic = new LinkedHashMap<>();

	// Map to columns of specific type.
	@Nonnull
	public Map<String, String[]> strings = new HashMap<>();
	@Nonnull
	public Map<String, Integer[]> integers = new HashMap<>();
	@Nonnull
	public Map<String, BigDecimal[]> decimals = new HashMap<>();
	@Nonnull
	public Map<String, ZonedDateTime[]> dates = new HashMap<>();

	public Data(@Nonnull Table table) {
		this.table = table;
	}

	public int size() {
		if (generic.isEmpty()) {
			return 0;
		}
		Object[] col = generic.entrySet().iterator().next().getValue();
		return col.length;
	}

	/**
	 * Rows of data values (just convenience views into this data object).
	 */
	@Nonnull
	public Stream<Row> rows() {
		return IntStream.range(0, size())
				.mapToObj(rowNr -> new Row(table, rowNr));
	}

	/**
	 * Columns that have data specified, in the same order as the data from {@link #generic} and {@link Row#cells()}.
	 */
	@Nonnull
	public Stream<Column> dataColumns() {
		return table.columns.stream()
				.filter(col -> table.data.generic.containsKey(col.nameLowercase));
	}

	@Nonnull
	public Stream<Column> nonDataColumns() {
		return table.columns.stream()
				.filter(col -> !table.data.generic.containsKey(col.nameLowercase));
	}
}
