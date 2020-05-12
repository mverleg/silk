
package nl.markv.silk.pojos.v0_3_0;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "description",
    "type",
    "nullable",
    "default_value",
    "auto_value"
})
public class LongColumn {

    /**
     * A name of a table, column or similar in the database.
     * (Required)
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("A name of a table, column or similar in the database.")
    public String name;
    @JsonProperty("description")
    public String description;
    /**
     * Type of the data that can be stored in the column.
     * (Required)
     * 
     */
    @JsonProperty("type")
    @JsonPropertyDescription("Type of the data that can be stored in the column.")
    public String type;
    /**
     * Whether the value can be null.
     * (Required)
     * 
     */
    @JsonProperty("nullable")
    @JsonPropertyDescription("Whether the value can be null.")
    public boolean nullable;
    /**
     * A literal column value (type is not checked by silk).
     * 
     */
    @JsonProperty("default_value")
    @JsonPropertyDescription("A literal column value (type is not checked by silk).")
    public String defaultValue;
    /**
     * Automatic way to fill the column.
     * 
     */
    @JsonProperty("auto_value")
    @JsonPropertyDescription("Automatic way to fill the column.")
    public LongColumn.AutoOptions autoValue;

    /**
     * No args constructor for use in serialization
     * 
     */
    public LongColumn() {
    }

    /**
     * 
     * @param nullable
     * @param defaultValue
     * @param name
     * @param description
     * @param type
     * @param autoValue
     */
    public LongColumn(String name, String description, String type, boolean nullable, String defaultValue, LongColumn.AutoOptions autoValue) {
        super();
        this.name = name;
        this.description = description;
        this.type = type;
        this.nullable = nullable;
        this.defaultValue = defaultValue;
        this.autoValue = autoValue;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(LongColumn.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("description");
        sb.append('=');
        sb.append(((this.description == null)?"<null>":this.description));
        sb.append(',');
        sb.append("type");
        sb.append('=');
        sb.append(((this.type == null)?"<null>":this.type));
        sb.append(',');
        sb.append("nullable");
        sb.append('=');
        sb.append(this.nullable);
        sb.append(',');
        sb.append("defaultValue");
        sb.append('=');
        sb.append(((this.defaultValue == null)?"<null>":this.defaultValue));
        sb.append(',');
        sb.append("autoValue");
        sb.append('=');
        sb.append(((this.autoValue == null)?"<null>":this.autoValue));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+(this.nullable? 1 : 0));
        result = ((result* 31)+((this.defaultValue == null)? 0 :this.defaultValue.hashCode()));
        result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
        result = ((result* 31)+((this.description == null)? 0 :this.description.hashCode()));
        result = ((result* 31)+((this.type == null)? 0 :this.type.hashCode()));
        result = ((result* 31)+((this.autoValue == null)? 0 :this.autoValue.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof LongColumn) == false) {
            return false;
        }
        LongColumn rhs = ((LongColumn) other);
        return ((((((this.nullable == rhs.nullable)&&((this.defaultValue == rhs.defaultValue)||((this.defaultValue!= null)&&this.defaultValue.equals(rhs.defaultValue))))&&((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name))))&&((this.description == rhs.description)||((this.description!= null)&&this.description.equals(rhs.description))))&&((this.type == rhs.type)||((this.type!= null)&&this.type.equals(rhs.type))))&&((this.autoValue == rhs.autoValue)||((this.autoValue!= null)&&this.autoValue.equals(rhs.autoValue))));
    }


    /**
     * Automatic way to fill the column.
     * 
     */
    public enum AutoOptions {

        INCREMENT("increment"),
        CREATED_TIMESTAMP("created_timestamp"),
        UPDATED_TIMESTAMP("updated_timestamp");
        private final String value;
        private final static Map<String, LongColumn.AutoOptions> CONSTANTS = new HashMap<String, LongColumn.AutoOptions>();

        static {
            for (LongColumn.AutoOptions c: values()) {
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
        public static LongColumn.AutoOptions fromValue(String value) {
            LongColumn.AutoOptions constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
