
package nl.markv.silk.pojos.v0_2_0;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UniqueConstraint implements Serializable
{

    @SerializedName("name")
    @Expose
    public String name;
    /**
     * The columns whose combination is unique
     * (Required)
     * 
     */
    @SerializedName("columns")
    @Expose
    public List<String> columns = new ArrayList<String>();
    private final static long serialVersionUID = 2108152404916374624L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public UniqueConstraint() {
    }

    /**
     * 
     * @param columns
     * @param name
     */
    public UniqueConstraint(String name, List<String> columns) {
        super();
        this.name = name;
        this.columns = columns;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(UniqueConstraint.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("columns");
        sb.append('=');
        sb.append(((this.columns == null)?"<null>":this.columns));
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
        result = ((result* 31)+((this.columns == null)? 0 :this.columns.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof UniqueConstraint) == false) {
            return false;
        }
        UniqueConstraint rhs = ((UniqueConstraint) other);
        return (((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name)))&&((this.columns == rhs.columns)||((this.columns!= null)&&this.columns.equals(rhs.columns))));
    }

}
