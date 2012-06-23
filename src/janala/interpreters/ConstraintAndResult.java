/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.interpreters;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/22/12
 * Time: 12:29 PM
 */
public class ConstraintAndResult {
    public final static ConstraintAndResult TRUE = new ConstraintAndResult(null,true);
    public final static ConstraintAndResult FALSE = new ConstraintAndResult(null,false);

    public Constraint constraint;
    public boolean result;

    public ConstraintAndResult(Constraint constraint, boolean result) {
        this.constraint = constraint;
        this.result = result;
    }

    @Override
    public String toString() {
        return "ConstraintAndResult{" +
                "constraint=" + constraint +
                ", result=" + result +
                '}';
    }
}
