
package nl.markv.silk.objects.v0_0_1;

import java.io.Serializable;

public class Column implements Serializable
{

    private final static long serialVersionUID = -8654410910159146583L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Column.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        if ((other instanceof Column) == false) {
            return false;
        }
        Column rhs = ((Column) other);
        return true;
    }

}
