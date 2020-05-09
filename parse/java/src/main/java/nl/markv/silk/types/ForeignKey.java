
package nl.markv.silk.types;

import java.util.ArrayList;
import java.util.List;
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
    "targetTable",
    "columns"
})
public class ForeignKey {

    @JsonProperty("name")
    public String name;
    @JsonProperty("targetTable")
    public String targetTableName;
    /**
     * The mapping of columns in the reference.
     */
    @JsonProperty("columns")
    @JsonPropertyDescription("The mapping of columns in the reference.")
    public List<ColumnMapping> columns = new ArrayList<ColumnMapping>();

    @JsonIgnore
    public Table targetTable;

    @JsonIgnore
    public Table sourceTable;

    public ForeignKey() {}

    //TODO @mark: generally be more consistent with source/target vs from/to naming
    public ForeignKey(@Nonnull Table sourceTable, @Nullable String name, @Nonnull Table targetTable, @Nonnull List<ColumnMapping> columns) {
        super();
        this.name = name;
        this.sourceTable = sourceTable;
        this.targetTable = targetTable;
        this.targetTableName = targetTable.name;
        this.columns = columns;
    }

    @Nonnull
    public List<Column> fromColumns() {
        return columns.stream()
                .map(m -> m.from)
                .collect(Collectors.toList());
    }

    @Nonnull
    public List<Column> toColumns() {
        return columns.stream()
                .map(m -> m.to)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        if (name != null) {
            return getClass().getSimpleName() + ":" + name + "(" + sourceTable + ">" + targetTableName + ")";
        }
        return getClass().getSimpleName() + ":(" + sourceTable + ">" + targetTableName + ")";
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceTable, name, targetTable, columns);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (!(other instanceof ForeignKey)) return false;
        return equals((ForeignKey) other);
    }

    public boolean equals(@Nonnull ForeignKey other) {
        return Objects.equals(name, other.name) &&
                Objects.equals(sourceTable, other.sourceTable) &&
                Objects.equals(targetTable, other.targetTable) &&
                Objects.equals(columns, other.columns);
    }
}
