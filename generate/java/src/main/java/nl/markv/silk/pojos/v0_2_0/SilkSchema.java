
package nl.markv.silk.pojos.v0_2_0;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * Silk
 * <p>
 * Cross-database relational database schema format
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "silk",
    "db",
    "table"
})
public class SilkSchema {

    /**
     * The semantic version of Silk that this data schema is exported in
     * 
     */
    @JsonProperty("silk")
    @JsonPropertyDescription("The semantic version of Silk that this data schema is exported in")
    public String silk;
    @JsonProperty("db")
    public Db db;
    @JsonProperty("table")
    public Table table;

    /**
     * No args constructor for use in serialization
     * 
     */
    public SilkSchema() {
    }

    /**
     * 
     * @param silk
     * @param db
     * @param table
     */
    public SilkSchema(String silk, Db db, Table table) {
        super();
        this.silk = silk;
        this.db = db;
        this.table = table;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(SilkSchema.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("silk");
        sb.append('=');
        sb.append(((this.silk == null)?"<null>":this.silk));
        sb.append(',');
        sb.append("db");
        sb.append('=');
        sb.append(((this.db == null)?"<null>":this.db));
        sb.append(',');
        sb.append("table");
        sb.append('=');
        sb.append(((this.table == null)?"<null>":this.table));
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
        result = ((result* 31)+((this.silk == null)? 0 :this.silk.hashCode()));
        result = ((result* 31)+((this.db == null)? 0 :this.db.hashCode()));
        result = ((result* 31)+((this.table == null)? 0 :this.table.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SilkSchema) == false) {
            return false;
        }
        SilkSchema rhs = ((SilkSchema) other);
        return ((((this.silk == rhs.silk)||((this.silk!= null)&&this.silk.equals(rhs.silk)))&&((this.db == rhs.db)||((this.db!= null)&&this.db.equals(rhs.db))))&&((this.table == rhs.table)||((this.table!= null)&&this.table.equals(rhs.table))));
    }

}
