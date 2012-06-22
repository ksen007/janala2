/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.interpreters;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/21/12
 * Time: 5:59 PM
 */
public class PointerConstraint {
    int first;
    int second;
    boolean isEqual;
    boolean value;

    public PointerConstraint(int first, int second, boolean equal, boolean value) {
        this.first = first;
        this.second = second;
        isEqual = equal;
        this.value = value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (first!=-1 && second!=-1)
            sb.append("p").append(first==0?"null":first).append(isEqual?"==":"!=").append("p").append(second==0?"null":second);
        else
            sb.append("null");
        return "PointerConstraint{" +
                "symbolic=" + sb.toString() +
                ", concrete=" + value +
                '}';
    }
}
