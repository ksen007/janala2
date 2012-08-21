/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package database.table;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */
public class DefaultGroupBy implements GroupBy {
    int i = 0;

    public Object[] groupBy(Row[] rows) {
        i++;
        return new Object[] {i};
    }
}
