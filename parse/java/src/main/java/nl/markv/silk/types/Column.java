
package nl.markv.silk.types;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "type",
    "nullable",
    "default_value",
    "auto_value"
})
public class Column {

    nl.markv.silk.pojos.v0_2_0.LongColumn pojo;

    /**
     * A name of a table, column or similar in the database
     * (Required)
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("A name of a table, column or similar in the database")
    public String name;
    /**
     * Type of the data that can be stored in the column
     */
    @JsonProperty("type")
    @JsonPropertyDescription("Type of the data that can be stored in the column")
    public String type;
    /**
     * Can the value be null
     */
    @JsonProperty("nullable")
    @JsonPropertyDescription("Can the value be null")
    public boolean nullable;
    /**
     * A literal column value (type is not checked by silk)
     * 
     */
    @JsonProperty("default_value")
    @JsonPropertyDescription("A literal column value (type is not checked by silk)")
    public String defaultValue;
    /**
     * Automatic way to fill the column
     * 
     */
    @JsonProperty("auto_value")
    @JsonPropertyDescription("Automatic way to fill the column")
    public AutoOptions autoValue;

    public Table table;

    public Column() {}

    public Column(@Nonnull Table table, @Nonnull String name, @Nonnull String type, boolean nullable, @Nullable String defaultValue, @Nullable AutoOptions autoValue) {
        super();
        this.table = table;
        this.name = name;
        this.type = type;
        this.nullable = nullable;
        this.defaultValue = defaultValue;
        this.autoValue = autoValue;
    }

    @Override
    public String toString() {
        return pojo.toString();
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
     * Automatic way to fill the column
     **/
    public enum AutoOptions {

        INCREMENT("increment"),
        CREATED_TIMESTAMP("created_timestamp"),
        UPDATED_TIMESTAMP("updated_timestamp");
        private final String value;
        private final static Map<String, AutoOptions> CONSTANTS = new HashMap<String, AutoOptions>();

        static {
            for (AutoOptions c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private AutoOptions(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static AutoOptions fromValue(String value) {
            AutoOptions constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
