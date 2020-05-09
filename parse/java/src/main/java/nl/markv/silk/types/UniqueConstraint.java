
package nl.markv.silk.types;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "columns"
})
public class UniqueConstraint {

    @JsonProperty("name")
    public String name;
    /**
     * The columns whose combination is unique
     */
    @JsonProperty("columns")
    @JsonPropertyDescription("The columns whose combination is unique")
    public List<String> columnsNames = new ArrayList<String>();

    public List<Column> columns;

    public Table table;

    public UniqueConstraint() { }

    public UniqueConstraint(@Nonnull Table table, @Nullable String name, @Nonnull List<Column> columns) {
        super();
        this.table = table;
        this.name = name;
        this.columns = columns;
        this.columnsNames = columns.stream()
                .map(col -> col.name)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        if (name != null) {
            return getClass().getSimpleName() + ":" + name + ":" + table.name + "(" + String.join(",", columnsNames) + ")";
        }
        return getClass().getSimpleName() + ":" + table.name + "(" + String.join(",", columnsNames) + ")";
    }

    @Override
    public int hashCode() {
        return Objects.hash(table, columns);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (!(other instanceof UniqueConstraint) ) return false;
        return equals((UniqueConstraint) other);
    }

    public boolean equals(@Nonnull UniqueConstraint other) {
        return Objects.equals(table, other.table) &&
                Objects.equals(name, other.name) &&
                Objects.equals(columns, other.columns);
    }
}
