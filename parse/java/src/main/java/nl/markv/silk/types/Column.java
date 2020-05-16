
package nl.markv.silk.types;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "type",
    "nullable",
    "default_value",
    "auto_value"
})
public class Column {

    /**
     * A name of a table, column or similar in the database
     */
    @JsonProperty("name")
    @JsonPropertyDescription("A name of a table, column or similar in the database")
    public String name;
    /**
     * Type of the data that can be stored in the column
     */
    @JsonProperty("type")
    @JsonPropertyDescription("Type of the data that can be stored in the column")
    public String typeName;
    /**
     * Can the value be null
     */
    @JsonProperty("nullable")
    @JsonPropertyDescription("Can the value be null")
    public boolean nullable;
    /**
     * A literal column value (type is not checked by silk)
     */
    @JsonProperty("default_value")
    @JsonPropertyDescription("A literal column value (type is not checked by silk)")
    public Object defaultValue;
    /**
     * Automatic way to fill the column
     */
    @JsonProperty("auto_value")
    @JsonPropertyDescription("Automatic way to fill the column")
    public AutoOptions autoValue;

    @JsonIgnore
    public DataType type;

    @JsonIgnore
    public Table table;

    @JsonIgnore
    public String nameLowercase;

    public Column() {}

    public Column(@Nonnull Table table, @Nonnull String name, @Nonnull DataType type, boolean nullable, @Nullable Object defaultValue, @Nullable AutoOptions autoValue) {
        super();
        this.table = table;
        this.name = name;
        this.nameLowercase = name.toLowerCase();
        this.type = type;
        this.typeName = type.toString();
        this.nullable = nullable;
        this.defaultValue = type.valueFromJson(defaultValue);
        this.autoValue = autoValue;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":" + table.name + "." + name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(table, name);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (!(other instanceof Column)) return false;
        return equals((Column) other);
    }

    public boolean equals(@Nonnull Column other) {
        return Objects.equals(table, other.table) &&
                Objects.equals(name, other.name) &&
                Objects.equals(type, other.type) &&
                nullable == other.nullable &&
                Objects.equals(defaultValue, other.defaultValue) &&
                Objects.equals(autoValue, other.autoValue);
    }

    /**
     * Automatic way to fill the column.
     **/
    public enum AutoOptions {

        INCREMENT("increment"),
        CREATED_TIMESTAMP("created_timestamp"),
        UPDATED_TIMESTAMP("updated_timestamp");

        @Nonnull
        public final String value;
        private final static Map<String, AutoOptions> CONSTANTS = new HashMap<String, AutoOptions>();

        static {
            for (AutoOptions c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        AutoOptions(@Nonnull String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonCreator
        @Nonnull
        public static AutoOptions fromValue(@Nonnull String value) {
            AutoOptions constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }
    }
}
