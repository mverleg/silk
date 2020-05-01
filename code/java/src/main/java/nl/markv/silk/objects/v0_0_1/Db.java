
package nl.markv.silk.objects.v0_0_1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Db implements Serializable
{

    /**
     * A name of a table, column or similar in the database
     * 
     */
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("description")
    @Expose
    public String description;
    /**
     * The tables in the database
     * 
     */
    @SerializedName("tables")
    @Expose
    public List<Table> tables = new ArrayList<Table>();
    /**
     * A name of a table, column or similar in the database
     * 
     */
    @SerializedName("database_type")
    @Expose
    public String databaseType;
    /**
     * Properties for the specific database, not controlled by Silk
     * 
     */
    @SerializedName("database_specific")
    @Expose
    public DatabaseSpecific databaseSpecific;
    private final static long serialVersionUID = -2349003417251044554L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Db() {
    }

    /**
     * 
     * @param databaseType
     * @param tables
     * @param name
     * @param description
     * @param databaseSpecific
     */
    public Db(String name, String description, List<Table> tables, String databaseType, DatabaseSpecific databaseSpecific) {
        super();
        this.name = name;
        this.description = description;
        this.tables = tables;
        this.databaseType = databaseType;
        this.databaseSpecific = databaseSpecific;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Db.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("description");
        sb.append('=');
        sb.append(((this.description == null)?"<null>":this.description));
        sb.append(',');
        sb.append("tables");
        sb.append('=');
        sb.append(((this.tables == null)?"<null>":this.tables));
        sb.append(',');
        sb.append("databaseType");
        sb.append('=');
        sb.append(((this.databaseType == null)?"<null>":this.databaseType));
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
        result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
        result = ((result* 31)+((this.databaseType == null)? 0 :this.databaseType.hashCode()));
        result = ((result* 31)+((this.description == null)? 0 :this.description.hashCode()));
        result = ((result* 31)+((this.tables == null)? 0 :this.tables.hashCode()));
        result = ((result* 31)+((this.databaseSpecific == null)? 0 :this.databaseSpecific.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Db) == false) {
            return false;
        }
        Db rhs = ((Db) other);
        return ((((((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name)))&&((this.databaseType == rhs.databaseType)||((this.databaseType!= null)&&this.databaseType.equals(rhs.databaseType))))&&((this.description == rhs.description)||((this.description!= null)&&this.description.equals(rhs.description))))&&((this.tables == rhs.tables)||((this.tables!= null)&&this.tables.equals(rhs.tables))))&&((this.databaseSpecific == rhs.databaseSpecific)||((this.databaseSpecific!= null)&&this.databaseSpecific.equals(rhs.databaseSpecific))));
    }

}
