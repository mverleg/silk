package nl.markv.silk.types;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static org.apache.commons.lang.Validate.isTrue;

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
	}

	public static class Timestamp extends DataType {}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
}
