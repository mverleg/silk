
package nl.markv.silk.types;

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
    "table",
    "columns"
})
public class ForeignKey {

    nl.markv.silk.pojos.v0_2_0.ForeignKey pojo;

    @JsonProperty("name")
    public String name;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("table")
    public String toTable;
    /**
     * The mapping of columns, from current table on the left, to target table on the right
     * (Required)
     * 
     */
    @JsonProperty("columns")
    @JsonPropertyDescription("The mapping of columns, from current table on the left, to target table on the right")
    public Columns columns;

    public Table fromTable;

    public ForeignKey() {}

    public ForeignKey(@Nonnull Table fromTable, @Nullable String name, @Nonnull String toTable, @Nonnull Columns columns) {
        super();
        this.name = name;
        this.fromTable = fromTable;
        this.toTable = toTable;
        this.columns = columns;
    }

    @Override
    public String toString() {
        return pojo.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, fromTable, toTable, columns);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (!(other instanceof ForeignKey)) return false;
        return equals((ForeignKey) other);
    }

    public boolean equals(@Nonnull ForeignKey other) {
        return Objects.equals(name, other.name)
                && Objects.equals(fromTable, other.fromTable)
                && Objects.equals(toTable, other.toTable)
                && Objects.equals(columns, other.columns);
    }
}
