
package nl.markv.silk.pojos.v0_2_0;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "table",
    "columns"
})
public class ForeignKey {

    @JsonProperty("name")
    public String name;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("table")
    public String table;
    /**
     * The mapping of columns, from current table on the left, to target table on the right
     * (Required)
     * 
     */
    @JsonProperty("columns")
    @JsonPropertyDescription("The mapping of columns, from current table on the left, to target table on the right")
    public Columns columns;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ForeignKey() {
    }

    /**
     * 
     * @param columns
     * @param name
     * @param table
     */
    public ForeignKey(String name, String table, Columns columns) {
        super();
        this.name = name;
        this.table = table;
        this.columns = columns;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ForeignKey.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("table");
        sb.append('=');
        sb.append(((this.table == null)?"<null>":this.table));
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
        result = ((result* 31)+((this.table == null)? 0 :this.table.hashCode()));
        result = ((result* 31)+((this.columns == null)? 0 :this.columns.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ForeignKey) == false) {
            return false;
        }
        ForeignKey rhs = ((ForeignKey) other);
        return ((((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name)))&&((this.table == rhs.table)||((this.table!= null)&&this.table.equals(rhs.table))))&&((this.columns == rhs.columns)||((this.columns!= null)&&this.columns.equals(rhs.columns))));
    }

}
