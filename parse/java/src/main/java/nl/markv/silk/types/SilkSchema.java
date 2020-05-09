package nl.markv.silk.types;

import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import static org.apache.commons.lang3.Validate.isTrue;

/**
 * Silk - Cross-database relational database schema format.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
		"silk",
		"db",
		"table"
})
public class SilkSchema {

	nl.markv.silk.pojos.v0_2_0.SilkSchema pojo;

	/**
	 * The semantic version of Silk that this data schema is exported in.
	 */
	@JsonProperty("silk")
	@JsonPropertyDescription("The semantic version of Silk that this data schema is exported in.")
	public String silkUrl;

	/**
	 * Only ONE of these two is set:
	 */
	@JsonProperty("db")
	public Db db;
	@JsonProperty("table")
	public Table table;

	public String schemaName;
	public String silkVersion;

	public SilkSchema() {}

	private SilkSchema(@Nonnull String schemaName, @Nonnull String silkUrl, @Nullable Db db, @Nullable Table table) {
		super();
		this.silkUrl = silkUrl;
		this.schemaName = schemaName;
		this.silkVersion = parseVersion(silkUrl);
		this.db = db;
		this.table = table;
	}

	@Nonnull
	public static SilkSchema db(@Nonnull String schemaName, @Nonnull String silkVersion, @Nonnull Db db) {
		return new SilkSchema(schemaName, silkVersion, db, null);
	}

	@Nonnull
	public static SilkSchema table(@Nonnull String schemaName, @Nonnull String silkVersion, @Nonnull Table table) {
		return new SilkSchema(schemaName, silkVersion, null, table);
	}

	public static String parseVersion(@Nonnull String url) {
		Matcher re = Pattern.compile("https://silk\\.markv\\.nl/v(\\d+)\\.(\\d+)\\.(\\d+)\\.json")
				.matcher(url);
		isTrue(re.matches(), "invalid Silk version url: " + url);
		return re.group(1) + "." + re.group(2) + "." + re.group(3);
	}

	@Nonnull
	public String name() {
		return schemaName;
	}

	@Override
	public String toString() {
		return schemaName;
	}

	@Nonnull
	public Iterable<Table> tables() {
		if (db != null) {
			return db.tables;
		} else {
			return Collections.singleton(table);
		}
	}
}
