
package nl.markv.silk.pojos.v0_3_0;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * A combination of a column in the source table (from) that refers to a column in the target table (to) as part of a foreign key reference.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "from",
    "to"
})
public class ColumnMapping {

    /**
     * A name of a table, column or similar in the database.
     * 
     */
    @JsonProperty("from")
    @JsonPropertyDescription("A name of a table, column or similar in the database.")
    public String from;
    /**
     * A name of a table, column or similar in the database.
     * 
     */
    @JsonProperty("to")
    @JsonPropertyDescription("A name of a table, column or similar in the database.")
    public String to;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ColumnMapping() {
    }

    /**
     * 
     * @param from
     * @param to
     */
    public ColumnMapping(String from, String to) {
        super();
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ColumnMapping.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("from");
        sb.append('=');
        sb.append(((this.from == null)?"<null>":this.from));
        sb.append(',');
        sb.append("to");
        sb.append('=');
        sb.append(((this.to == null)?"<null>":this.to));
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
        result = ((result* 31)+((this.from == null)? 0 :this.from.hashCode()));
        result = ((result* 31)+((this.to == null)? 0 :this.to.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ColumnMapping) == false) {
            return false;
        }
        ColumnMapping rhs = ((ColumnMapping) other);
        return (((this.from == rhs.from)||((this.from!= null)&&this.from.equals(rhs.from)))&&((this.to == rhs.to)||((this.to!= null)&&this.to.equals(rhs.to))));
    }

}
