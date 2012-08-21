/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package database.table;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 8/21/12
 * Time: 2:36 PM
 */
public interface Operations {
    public Object apply(Object old, Row[] rows);
}
