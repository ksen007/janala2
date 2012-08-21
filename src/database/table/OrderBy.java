/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package database.table;

import java.util.Comparator;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 8/20/12
 * Time: 12:46 PM
 */
public interface OrderBy {
    public Comparator getComparator();
}
