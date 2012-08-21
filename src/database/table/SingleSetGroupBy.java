/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package database.table;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */
public class SingleSetGroupBy implements GroupBy {
    public Object[] groupBy(Row[] rows) {
        return new Object[]{1};
    }
}
