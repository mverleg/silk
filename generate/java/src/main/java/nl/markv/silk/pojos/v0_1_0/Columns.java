
package nl.markv.silk.pojos.v0_1_0;

import java.io.Serializable;


/**
 * The mapping of columns, from current table on the left, to target table on the right
 * 
 */
public class Columns implements Serializable
{

    private final static long serialVersionUID = -8055204204841575233L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Columns.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        if ((other instanceof Columns) == false) {
            return false;
        }
        Columns rhs = ((Columns) other);
        return true;
    }

}
