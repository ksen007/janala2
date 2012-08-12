/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package database.table;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 7/2/12
 * Time: 10:42 AM
 */
public abstract class Where {
    public abstract boolean isTrue(Row[] rows);
    public boolean modify(Row row) {
        throw new RuntimeException("Unimplemented modify in Where class");
    }

}
