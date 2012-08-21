/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package database.table;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 8/20/12
 * Time: 12:45 PM
 */
public interface GroupBy {
    public Object[] groupBy(Row[] rows);
}
