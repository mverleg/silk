
package nl.markv.silk.types;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * Properties for the specific database, not controlled by Silk.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({})
public class DatabaseSpecific {

    nl.markv.silk.pojos.v0_2_0.DatabaseSpecific pojo;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    /* Only set if these are table-specific properties. */
    public Table table;

    public DatabaseSpecific() { }

    public DatabaseSpecific(@Nullable Table table, @Nullable Map<String, Object> additionalProperties) {
        this.table = table;
        if (additionalProperties != null) {
            this.additionalProperties = additionalProperties;
        }
    }

    @Override
    public String toString() {
        return pojo.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(table, additionalProperties);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (!(other instanceof DatabaseSpecific)) return false;
        return equals((DatabaseSpecific) other);
    }

    public boolean equals(@Nonnull DatabaseSpecific other) {
        return Objects.equals(table, other.table)
                && Objects.equals(additionalProperties, other.additionalProperties);
    }
}
