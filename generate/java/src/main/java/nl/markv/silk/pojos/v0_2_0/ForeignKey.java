
package nl.markv.silk.pojos.v0_2_0;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "targetTable",
    "columns"
})
public class ForeignKey {

    @JsonProperty("name")
    public String name;
    @JsonProperty("targetTable")
    public String targetTable;
    /**
     * The mapping of columns in the reference.
     * (Required)
     * 
     */
    @JsonProperty("columns")
    @JsonPropertyDescription("The mapping of columns in the reference.")
    public List<ColumnMapping> columns = new ArrayList<ColumnMapping>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public ForeignKey() {
    }

    /**
     * 
     * @param targetTable
     * @param columns
     * @param name
     */
    public ForeignKey(String name, String targetTable, List<ColumnMapping> columns) {
        super();
        this.name = name;
        this.targetTable = targetTable;
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
        sb.append("targetTable");
        sb.append('=');
        sb.append(((this.targetTable == null)?"<null>":this.targetTable));
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
        result = ((result* 31)+((this.targetTable == null)? 0 :this.targetTable.hashCode()));
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
        return ((((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name)))&&((this.targetTable == rhs.targetTable)||((this.targetTable!= null)&&this.targetTable.equals(rhs.targetTable))))&&((this.columns == rhs.columns)||((this.columns!= null)&&this.columns.equals(rhs.columns))));
    }

}
