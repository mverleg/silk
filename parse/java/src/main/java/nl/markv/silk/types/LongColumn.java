//TODO @mark

package nl.markv.silk.types;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LongColumn implements Serializable
{

    /**
     * A name of a table, column or similar in the database
     * (Required)
     * 
     */
    @SerializedName("name")
    @Expose
    public String name;
    /**
     * Type of the data that can be stored in the column
     * (Required)
     * 
     */
    @SerializedName("type")
    @Expose
    public String type;
    /**
     * Can the value be null
     * (Required)
     * 
     */
    @SerializedName("nullable")
    @Expose
    public boolean nullable;
    /**
     * A literal column value (type is not checked by silk)
     * 
     */
    @SerializedName("default_value")
    @Expose
    public String defaultValue;
    /**
     * Automatic way to fill the column
     * 
     */
    @SerializedName("auto_value")
    @Expose
    public AutoOptions autoValue;
    private final static long serialVersionUID = -6146141790791519789L;

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
     * @param type
     * @param autoValue
     */
    public LongColumn(String name, String type, boolean nullable, String defaultValue, AutoOptions autoValue) {
        super();
        this.name = name;
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
        result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
        result = ((result* 31)+((this.type == null)? 0 :this.type.hashCode()));
        result = ((result* 31)+(this.nullable? 1 : 0));
        result = ((result* 31)+((this.defaultValue == null)? 0 :this.defaultValue.hashCode()));
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
        return ((((((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name)))&&((this.type == rhs.type)||((this.type!= null)&&this.type.equals(rhs.type))))&&(this.nullable == rhs.nullable))&&((this.defaultValue == rhs.defaultValue)||((this.defaultValue!= null)&&this.defaultValue.equals(rhs.defaultValue))))&&((this.autoValue == rhs.autoValue)||((this.autoValue!= null)&&this.autoValue.equals(rhs.autoValue))));
    }


    /**
     * Automatic way to fill the column
     * 
     */
    public enum AutoOptions {

        @SerializedName("increment")
        INCREMENT("increment"),
        @SerializedName("created_timestamp")
        CREATED_TIMESTAMP("created_timestamp"),
        @SerializedName("updated_timestamp")
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

        public String value() {
            return this.value;
        }

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
