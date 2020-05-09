
package nl.markv.silk.pojos.v0_2_0;

import java.io.Serializable;


/**
 * Properties for the specific database, not controlled by Silk
 * 
 */
public class DatabaseSpecific implements Serializable
{

    private final static long serialVersionUID = -6258693548568838608L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(DatabaseSpecific.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        if ((other instanceof DatabaseSpecific) == false) {
            return false;
        }
        DatabaseSpecific rhs = ((DatabaseSpecific) other);
        return true;
    }

}
