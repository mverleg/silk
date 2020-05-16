package nl.markv.silk.types;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Nonnull;

public class Data {
	// Mapping from lowercase name to a column of data.

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

	public int size() {
		if (generic.isEmpty()) {
			return 0;
		}
		Object[] col = generic.entrySet().iterator().next().getValue();
		return col.length;
	}
}
