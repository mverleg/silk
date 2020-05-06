package nl.markv.silk.parse;

import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;

import nl.markv.silk.pojos.v0_1_0.SilkSchema;
import nl.markv.silk.pojos.v0_1_0.Table;

import static org.apache.commons.lang3.Validate.isTrue;

public class SilkDb {

	private final SilkSchema schema;

	private SilkDb(@Nonnull SilkSchema schema) {
		this.schema = schema;
	}

	public static SilkDb wrap(@Nonnull SilkSchema schema) {
		return new SilkDb(schema);
	}

	public String version() {
		Matcher re = Pattern.compile("https://silk\\.markv\\.nl/v(\\d+)\\.(\\d+)\\.(\\d+)\\.json")
				.matcher(schema.silk);
		isTrue(re.matches(), "invalid Silk version url: " + schema.silk);
		return re.group(1) + "." + re.group(2) + "." + re.group(3);
	}

	@Nonnull
	public Iterable<Table> tables() {
		if (schema.db != null) {
			return schema.db.tables;
		} else {
			return Collections.singleton(schema.table);
		}
	}

	@Override
	public String toString() {
		return schema.toString();
	}
}
