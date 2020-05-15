
package nl.markv.silk.types;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    /**
     * A name of a table, column or similar in the database
     */
    @JsonProperty("name")
    @JsonPropertyDescription("A name of a table, column or similar in the database")
    public String name;
    /**
     * A name of a table, column or similar in the database
     */
    @JsonProperty("group")
    @JsonPropertyDescription("A name of a table, column or similar in the database")
    public String group;
    @JsonProperty("description")
    public String description;
    @JsonProperty("columns")
    public List<Column> columns = new ArrayList<Column>();
    @JsonProperty("primary_key")
    public List<String> primaryKeyNames = new ArrayList<String>();
    @JsonProperty("references")
    public List<ForeignKey> references = new ArrayList<ForeignKey>();
    @JsonProperty("unique_constraints")
    public List<UniqueConstraint> uniqueConstraints = new ArrayList<UniqueConstraint>();
    @JsonProperty("check_constraints")
    public List<CheckConstraint> checkConstraints = new ArrayList<CheckConstraint>();
    /**
     * Properties for the specific database, not controlled by Silk
     */
    @JsonProperty("database_specific")
    @JsonPropertyDescription("Properties for the specific database, not controlled by Silk")
    public DatabaseSpecific databaseSpecific = new DatabaseSpecific(this, null);

    /** Only set if the schema included a whole database (as opposed to one table). */
    @JsonIgnore
    public Db database;

    @JsonIgnore
    public List<Column> primaryKey;

    @JsonIgnore
    public Map<String, Column> columnLookupLowercase;

    @JsonIgnore
    public List<ForeignKey> incomingReferences = new ArrayList<>();

    public Table() {}

    public Table(
            @Nullable Db database,
            @Nonnull String name,
            @Nonnull String group,
            @Nullable String description,
            @Nonnull List<Column> columns,
            @Nonnull List<Column> primaryKey,
            @Nullable List<ForeignKey> references,
            @Nullable List<UniqueConstraint> uniqueConstraints,
            @Nullable List<CheckConstraint> checkConstraints,
            @Nullable DatabaseSpecific databaseSpecific
    ) {
        super();
        this.database = database;
        this.name = name;
        this.group = group;
        this.description = description;
        this.columns = columns;
        this.columnLookupLowercase = columns.stream()
                .collect(Collectors.toMap(c -> c.name.toLowerCase(), c -> c));
        this.primaryKey = primaryKey;
        this.primaryKeyNames = primaryKey.stream()
                .map(col -> col.name)
                .collect(Collectors.toList());
        if (references != null) {
            this.references = references;
        }
        if (uniqueConstraints != null) {
            this.uniqueConstraints = uniqueConstraints;
        }
        if (checkConstraints != null) {
            this.checkConstraints = checkConstraints;
        }
        if (databaseSpecific != null) {
            this.databaseSpecific = databaseSpecific;
        }
    }

    @Override
    public String toString() {
        if (group != null) {
            return getClass().getSimpleName() + ":" + group + "." + name;
        }
        return getClass().getSimpleName() + ":" + name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(database, group, name);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (!(other instanceof Table) ) return false;
        return equals((Table) other);
    }

    public boolean equals(@Nonnull Table other) {
        return Objects.equals(database, other.database) &&
                Objects.equals(name, other.name) &&
                Objects.equals(group, other.group) &&
                Objects.equals(description, other.description) &&
                Objects.equals(columns, other.columns) &&
                Objects.equals(primaryKey, other.primaryKey) &&
                Objects.equals(references, other.references) &&
                Objects.equals(uniqueConstraints, other.uniqueConstraints) &&
                Objects.equals(checkConstraints, other.checkConstraints) &&
                Objects.equals(databaseSpecific, other.databaseSpecific);
    }
}
