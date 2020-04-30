
package nl.markv.silk.objects.v0_0_1;

import java.io.Serializable;

public class UniqueConstraint implements Serializable
{

    private final static long serialVersionUID = 8907263485624135053L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(UniqueConstraint.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof UniqueConstraint) == false) {
            return false;
        }
        UniqueConstraint rhs = ((UniqueConstraint) other);
        return true;
    }

}
