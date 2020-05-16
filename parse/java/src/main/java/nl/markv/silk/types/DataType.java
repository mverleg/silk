package nl.markv.silk.types;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static java.time.format.DateTimeFormatter.ISO_DATE;
import static java.time.format.DateTimeFormatter.ISO_INSTANT;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;
import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;
import static java.time.format.DateTimeFormatter.ISO_ZONED_DATE_TIME;
import static java.util.Arrays.asList;
import static org.apache.commons.lang.Validate.isTrue;

//TODO @mark: equals / hashCode?
public abstract class DataType {

	static final Pattern TEXT_RE = Pattern.compile("^text\\((\\d+)\\)$");
	static final Pattern DECIMAL1_RE = Pattern.compile("^decimal\\((\\d+)\\)$");
	static final Pattern DECIMAL2_RE = Pattern.compile("^decimal\\((\\d+),\\s*(\\d+)\\)$");

	@Nonnull
	public static DataType fromSilkString(@Nonnull String typeStr) {
		if ("text".equals(typeStr)) {
			return new Text();
		}
		if ("int".equals(typeStr)) {
			return Int.signed();
		}
		if ("uint".equals(typeStr)) {
			return Int.unsigned();
		}
		if ("decimal".equals(typeStr)) {
			return new Decimal(null, null);
		}
		if ("timestamp".equals(typeStr)) {
			return new Timestamp();
		}
		Matcher matcher = TEXT_RE.matcher(typeStr);
		if (matcher.matches()) {
			return new Text(Integer.parseInt(matcher.group(1)));
		}
		matcher = DECIMAL1_RE.matcher(typeStr);
		if (matcher.matches()) {
			return new Decimal(Integer.parseInt(matcher.group(1)), null);
		}
		matcher = DECIMAL2_RE.matcher(typeStr);
		if (matcher.matches()) {
			return new Decimal(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
		}
		throw new UnsupportedOperationException("unknown data type: " + typeStr);
	}

	private DataType() {
	}

	public static class Text extends DataType {

		public final Integer maxLength;

		public Text(int maxLength) {
			this.maxLength = maxLength;
		}

		public Text() {
			this.maxLength = null;
		}

		@Override
		public String toString() {
			if (maxLength == null) {
				return "text";
			}
			return "text(" + maxLength + ")";
		}
	}

	public static class Int extends DataType {

		public final Integer min;
		public final Integer max;

		private Int(Integer min, Integer max) {
			this.min = min;
			this.max = max;
		}

		public static Int range(Integer min, Integer max) {
			return new Int(min, max);
		}

		public static Int signed() {
			return range(null, null);
		}

		public static Int unsigned(Integer max) {
			return range(0, max);
		}

		public static Int unsigned() {
			return unsigned(null);
		}

		@Override
		public String toString() {
			if (min == null) {
				return "int";
			}
			isTrue(min == 0);
			return "uint";
		}

		@Nullable
		public Integer valueFromStr(@Nullable String txt) {
			if (txt == null) {
				return null;
			}
			try {
				return Integer.parseInt(txt);
			} catch (NumberFormatException ex) {
				throw new IllegalArgumentException("Got value '" + txt + "' where an integer was expected");
			}
		}
	}

	public static class Decimal extends DataType {

		// Number of digits in total.
		public final Integer precision;
		// Number of digits after decimal.
		public final Integer scale;

		public Decimal(@Nullable Integer precision, @Nullable Integer scale) {
			if (precision != null) {
				isTrue(precision > 0);
			}
			if (scale != null) {
				isTrue(precision != null);
				isTrue(precision >= scale);
				isTrue(scale >= 0);
			}
			this.precision = precision;
			this.scale = scale;
		}

		@Override
		public String toString() {
			if (scale != null) {
				return "decimal(" + precision + "," + scale + ")";
			}
			if (precision != null) {
				return "decimal(" + precision + ")";
			}
			return "decimal";
		}

		@Nullable
		public BigDecimal valueFromStr(@Nullable String txt) {
			if (txt == null) {
				return null;
			}
			//TODO @mark: fix scale
			return new BigDecimal(txt);
		}
	}

	public static class Timestamp extends DataType {
		private static final Iterable<? extends DateTimeFormatter> DATE_TIME_FORMATS = asList(
				ISO_INSTANT,
				ISO_DATE,
				ISO_LOCAL_DATE,
				ISO_LOCAL_DATE_TIME,
				ISO_OFFSET_DATE_TIME,
				ISO_ZONED_DATE_TIME
		);

		@Override
		public String toString() {
			return "timestamp";
		}

		@Nullable
		public LocalDateTime valueFromStr(@Nullable String txt) {
			if (txt == null) {
				return null;
			}
			LocalDateTime dt = null;
			for (DateTimeFormatter format : DATE_TIME_FORMATS) {
				try {
					dt = LocalDateTime.parse(txt, format);
				} catch (DateTimeParseException ex) {
					// Try the next format
				}
			}
			if (dt == null) {
				throw new IllegalArgumentException("Got value '" + txt +
						"' where a iso datetime was expected, e.g. '2011-12-03T10:15:30Z'");
			}
			return dt;
		}
	}

	@Override
	public abstract String toString();
}
