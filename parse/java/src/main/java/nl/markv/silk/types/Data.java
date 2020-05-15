package nl.markv.silk.types;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

public class Data {
	// Mapping from lowercase name to a column of data.

	// Map to columns of data (as Objects, because the type may differ).
	@Nonnull
	Map<String, Object[]> generic = new HashMap<>();

	// Map to columns of specific type.
	@Nonnull
	Map<String, String[]> strings = new HashMap<>();
	@Nonnull
	Map<String, LocalDate[]> dates = new HashMap<>();
	@Nonnull
	Map<String, BigDecimal[]> decimals = new HashMap<>();
	@Nonnull
	Map<String, double[]> doubles = new HashMap<>();
	@Nonnull
	Map<String, int[]> integers = new HashMap<>();

}
