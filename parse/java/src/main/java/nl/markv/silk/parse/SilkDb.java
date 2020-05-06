package nl.markv.silk.parse;

import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;

import nl.markv.silk.pojos.v0_1_0.SilkSchema;
import nl.markv.silk.pojos.v0_1_0.Table;
import nl.markv.silk.types.DataType;

import static org.apache.commons.lang3.Validate.isTrue;

public class SilkDb {

	private final String name;
	private final SilkSchema schema;

	private SilkDb(@Nonnull String name, @Nonnull SilkSchema schema) {
		this.name = name;
		this.schema = schema;
	}

	public static SilkDb wrap(@Nonnull SilkSchema schema) {
		return new SilkDb("(nameless Silk schema)", schema);
	}

	public static SilkDb wrap(@Nonnull String name, @Nonnull SilkSchema schema) {
		return new SilkDb(name, schema);
	}

	public String version() {
		Matcher re = Pattern.compile("https://silk\\.markv\\.nl/v(\\d+)\\.(\\d+)\\.(\\d+)\\.json")
				.matcher(schema.silk);
		isTrue(re.matches(), "invalid Silk version url: " + schema.silk);
		return re.group(1) + "." + re.group(2) + "." + re.group(3);
	}

	@Nonnull
	public DataType parseDataType(@Nonnull String dataTypeText) {
		return DataType.fromSilkString(dataTypeText);
	}

	@Nonnull
	public String name() {
		return this.name;
	}

	@Override
	public String toString() {
		return schema.toString();
	}

	@Nonnull
	public Iterable<Table> tables() {
		if (schema.db != null) {
			return schema.db.tables;
		} else {
			return Collections.singleton(schema.table);
		}
	}
}
