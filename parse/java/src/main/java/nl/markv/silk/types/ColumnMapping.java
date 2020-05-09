
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
    "from",
    "to"
})
public class ColumnMapping {

    /**
     * A column in the table that the foreign key belongs to.
     */
    @JsonProperty("from")
    @JsonPropertyDescription("A column in the table that the foreign key belongs to.")
    public String fromName;
    /**
     * A column in the target table that the 'from' column refers to.
     */
    @JsonProperty("to")
    @JsonPropertyDescription("A column in the target table that the 'from' column refers to.")
    public String toName;

    public ForeignKey foreignKey;
    public Column from;
    public Column to;

    public ColumnMapping() {}

    // 'foreignKey' is required but may be filled later due to cyclic references
    public ColumnMapping(@Nullable ForeignKey foreignKey, @Nonnull Column from, @Nonnull Column to) {
        super();
        this.foreignKey = foreignKey;
        this.from = from;
        this.fromName = from.name;
        this.to = to;
        this.toName = to.name;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":" + fromName + ">" + toName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(foreignKey.sourceTable, foreignKey.targetTable, fromName, toName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (!(other instanceof ColumnMapping)) return false;
        return equals((ColumnMapping) other);
    }

    public boolean equals(@Nonnull ColumnMapping other) {
        return Objects.equals(foreignKey.sourceTable, other.foreignKey.sourceTable) &&
                Objects.equals(foreignKey.targetTable, other.foreignKey.targetTable) &&
                Objects.equals(fromName, other.fromName) &&
                Objects.equals(toName, other.toName);
    }
}
