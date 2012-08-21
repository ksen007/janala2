/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package database.table;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */
public class WhereTrue extends Where {
    @Override
    public boolean where(Row[] rows) {
        return true;
    }
}