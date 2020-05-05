
package nl.markv.silk.pojos.v0_1_0;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckConstraint implements Serializable
{

    @SerializedName("name")
    @Expose
    public String name;
    /**
     * A boolean sql condition (cross-database syntax)
     * (Required)
     * 
     */
    @SerializedName("condition")
    @Expose
    public String condition;
    private final static long serialVersionUID = 4987772636935005288L;

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
