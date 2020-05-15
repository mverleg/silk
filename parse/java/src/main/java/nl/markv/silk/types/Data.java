package nl.markv.silk.types;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

public class Data {
	// Mapping from lowercase name to a column of data.

	// Map to columns of data (as Objects, because the type may differ).
	@Nonnull
	public Map<String, Object[]> generic = new HashMap<>();

	// Map to columns of specific type.
	@Nonnull
	public Map<String, String[]> strings = new HashMap<>();
	@Nonnull
	public Map<String, LocalDateTime[]> dates = new HashMap<>();
	@Nonnull
	public Map<String, BigDecimal[]> decimals = new HashMap<>();
	@Nonnull
	public Map<String, Double[]> doubles = new HashMap<>();
	@Nonnull
	public Map<String, Integer[]> integers = new HashMap<>();

}
