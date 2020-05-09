
package nl.markv.silk.pojos.v0_2_0;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "from",
    "to"
})
public class ColumnMapping {

    /**
     * A column in the table that the foreign key belongs to.
     * 
     */
    @JsonProperty("from")
    @JsonPropertyDescription("A column in the table that the foreign key belongs to.")
    public String from;
    /**
     * A column in the target table that the 'from' column refers to.
     * 
     */
    @JsonProperty("to")
    @JsonPropertyDescription("A column in the target table that the 'from' column refers to.")
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
