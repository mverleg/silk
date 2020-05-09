
package nl.markv.silk.pojos.v0_2_0;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "condition"
})
public class CheckConstraint {

    @JsonProperty("name")
    public String name;
    /**
     * A boolean sql condition (cross-database syntax)
     * (Required)
     * 
     */
    @JsonProperty("condition")
    @JsonPropertyDescription("A boolean sql condition (cross-database syntax)")
    public String condition;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CheckConstraint() {
    }

    /**
     * 
     * @param condition
     * @param name
     */
    public CheckConstraint(String name, String condition) {
        super();
        this.name = name;
        this.condition = condition;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(CheckConstraint.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("condition");
        sb.append('=');
        sb.append(((this.condition == null)?"<null>":this.condition));
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
        result = ((result* 31)+((this.condition == null)? 0 :this.condition.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof CheckConstraint) == false) {
            return false;
        }
        CheckConstraint rhs = ((CheckConstraint) other);
        return (((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name)))&&((this.condition == rhs.condition)||((this.condition!= null)&&this.condition.equals(rhs.condition))));
    }

}
