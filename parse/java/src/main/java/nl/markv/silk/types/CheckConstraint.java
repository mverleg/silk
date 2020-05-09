
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
    "condition"
})
public class CheckConstraint {

    nl.markv.silk.pojos.v0_2_0.CheckConstraint pojo;

    @JsonProperty("name")
    public String name;
    /**
     * A boolean sql condition (cross-database syntax)
     */
    @JsonProperty("condition")
    @JsonPropertyDescription("A boolean sql condition (cross-database syntax)")
    public String condition;

    public Table table;

    public CheckConstraint() {}

    public CheckConstraint(@Nonnull Table table, @Nullable String name, @Nonnull String condition) {
        super();
        this.table = table;
        this.name = name;
        this.condition = condition;
    }

    @Override
    public String toString() {
        return pojo.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(table, condition);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (!(other instanceof CheckConstraint)) return false;
        return equals((CheckConstraint) other);
    }

    public boolean equals(@Nonnull CheckConstraint other) {
        return Objects.equals(table, other.table) &&
                Objects.equals(name, other.name) &&
                Objects.equals(condition, other.condition);
    }
}
