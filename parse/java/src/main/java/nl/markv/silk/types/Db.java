
package nl.markv.silk.types;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "description",
    "tables",
    "database_type",
    "database_specific"
})
public class Db {

    /**
     * A name of a table, column or similar in the database
     */
    @JsonProperty("name")
    @JsonPropertyDescription("A name of a table, column or similar in the database")
    public String name;
    @JsonProperty("description")
    public String description;
    /**
     * The tables in the database
     *
     * Note that, if there are no dependency cycles, then these tables are in order of dependencies.
     * Therefore when generating (again, if no cyclic dependencies), foreign keys and data can be added at
     * table creation since all tables being referred should already exist. Still, if the database supports it,
     * it is safer to add all tables, then all data, then all foreign keys, for speed and circular references.
     */
    @JsonProperty("tables")
    @JsonPropertyDescription("The tables in the database")
    public List<Table> tables = new ArrayList<>();
    /**
     * A name of a table, column or similar in the database
     */
    @JsonProperty("database_type")
    @JsonPropertyDescription("A name of a table, column or similar in the database")
    public String databaseType;
    /**
     * Properties for the specific database, not controlled by Silk
     */
    @JsonProperty("database_specific")
    @JsonPropertyDescription("Properties for the specific database, not controlled by Silk")
    public DatabaseSpecific databaseSpecific = new DatabaseSpecific(null, null);

    public Db() {}

    public Db(@Nonnull String name, @Nullable String description, @Nonnull List<Table> tables, @Nullable String databaseType, @Nullable DatabaseSpecific databaseSpecific) {
        super();
        this.name = name;
        this.description = description;
        this.tables = tables;
        this.databaseType = databaseType;
        if (databaseSpecific != null) {
            this.databaseSpecific = databaseSpecific;
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":" + name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, databaseType);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (!(other instanceof Db)) return false;
        return equals((Db) other);
    }

    public boolean equals(@Nonnull Db other) {
        return Objects.equals(name, other.name) &&
            Objects.equals(description, other.description) &&
            Objects.equals(tables, other.tables) &&
            Objects.equals(databaseType, other.databaseType) &&
            Objects.equals(databaseSpecific, other.databaseSpecific);
    }
}
