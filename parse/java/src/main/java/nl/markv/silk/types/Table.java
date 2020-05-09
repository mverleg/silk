
package nl.markv.silk.types;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "group",
    "description",
    "columns",
    "primary_key",
    "references",
    "unique_constraints",
    "check_constraints",
    "database_specific"
})
public class Table {

    nl.markv.silk.pojos.v0_2_0.Table pojo;

    /**
     * A name of a table, column or similar in the database
     * (Required)
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("A name of a table, column or similar in the database")
    public String name;
    /**
     * A name of a table, column or similar in the database
     * 
     */
    @JsonProperty("group")
    @JsonPropertyDescription("A name of a table, column or similar in the database")
    public String group;
    @JsonProperty("description")
    public String description;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("columns")
    public List<LongColumn> columns = new ArrayList<LongColumn>();
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("primary_key")
    public List<String> primaryKey = new ArrayList<String>();
    @JsonProperty("references")
    public List<ForeignKey> references = new ArrayList<ForeignKey>();
    @JsonProperty("unique_constraints")
    public List<UniqueConstraint> uniqueConstraints = new ArrayList<UniqueConstraint>();
    @JsonProperty("check_constraints")
    public List<CheckConstraint> checkConstraints = new ArrayList<CheckConstraint>();
    /**
     * Properties for the specific database, not controlled by Silk
     * 
     */
    @JsonProperty("database_specific")
    @JsonPropertyDescription("Properties for the specific database, not controlled by Silk")
    public DatabaseSpecific databaseSpecific;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Table() {
    }

    /**
     * 
     * @param checkConstraints
     * @param references
     * @param uniqueConstraints
     * @param columns
     * @param name
     * @param description
     * @param group
     * @param primaryKey
     * @param databaseSpecific
     */
    public Table(String name, String group, String description, List<LongColumn> columns, List<String> primaryKey, List<ForeignKey> references, List<UniqueConstraint> uniqueConstraints, List<CheckConstraint> checkConstraints, DatabaseSpecific databaseSpecific) {
        super();
        this.name = name;
        this.group = group;
        this.description = description;
        this.columns = columns;
        this.primaryKey = primaryKey;
        this.references = references;
        this.uniqueConstraints = uniqueConstraints;
        this.checkConstraints = checkConstraints;
        this.databaseSpecific = databaseSpecific;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Table.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("group");
        sb.append('=');
        sb.append(((this.group == null)?"<null>":this.group));
        sb.append(',');
        sb.append("description");
        sb.append('=');
        sb.append(((this.description == null)?"<null>":this.description));
        sb.append(',');
        sb.append("columns");
        sb.append('=');
        sb.append(((this.columns == null)?"<null>":this.columns));
        sb.append(',');
        sb.append("primaryKey");
        sb.append('=');
        sb.append(((this.primaryKey == null)?"<null>":this.primaryKey));
        sb.append(',');
        sb.append("references");
        sb.append('=');
        sb.append(((this.references == null)?"<null>":this.references));
        sb.append(',');
        sb.append("uniqueConstraints");
        sb.append('=');
        sb.append(((this.uniqueConstraints == null)?"<null>":this.uniqueConstraints));
        sb.append(',');
        sb.append("checkConstraints");
        sb.append('=');
        sb.append(((this.checkConstraints == null)?"<null>":this.checkConstraints));
        sb.append(',');
        sb.append("databaseSpecific");
        sb.append('=');
        sb.append(((this.databaseSpecific == null)?"<null>":this.databaseSpecific));
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
        result = ((result* 31)+((this.checkConstraints == null)? 0 :this.checkConstraints.hashCode()));
        result = ((result* 31)+((this.references == null)? 0 :this.references.hashCode()));
        result = ((result* 31)+((this.uniqueConstraints == null)? 0 :this.uniqueConstraints.hashCode()));
        result = ((result* 31)+((this.columns == null)? 0 :this.columns.hashCode()));
        result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
        result = ((result* 31)+((this.description == null)? 0 :this.description.hashCode()));
        result = ((result* 31)+((this.group == null)? 0 :this.group.hashCode()));
        result = ((result* 31)+((this.primaryKey == null)? 0 :this.primaryKey.hashCode()));
        result = ((result* 31)+((this.databaseSpecific == null)? 0 :this.databaseSpecific.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Table) == false) {
            return false;
        }
        Table rhs = ((Table) other);
        return ((((((((((this.checkConstraints == rhs.checkConstraints)||((this.checkConstraints!= null)&&this.checkConstraints.equals(rhs.checkConstraints)))&&((this.references == rhs.references)||((this.references!= null)&&this.references.equals(rhs.references))))&&((this.uniqueConstraints == rhs.uniqueConstraints)||((this.uniqueConstraints!= null)&&this.uniqueConstraints.equals(rhs.uniqueConstraints))))&&((this.columns == rhs.columns)||((this.columns!= null)&&this.columns.equals(rhs.columns))))&&((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name))))&&((this.description == rhs.description)||((this.description!= null)&&this.description.equals(rhs.description))))&&((this.group == rhs.group)||((this.group!= null)&&this.group.equals(rhs.group))))&&((this.primaryKey == rhs.primaryKey)||((this.primaryKey!= null)&&this.primaryKey.equals(rhs.primaryKey))))&&((this.databaseSpecific == rhs.databaseSpecific)||((this.databaseSpecific!= null)&&this.databaseSpecific.equals(rhs.databaseSpecific))));
    }

}
