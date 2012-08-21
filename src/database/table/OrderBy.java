/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package database.table;

import java.util.Comparator;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */
public interface OrderBy {
    public Comparator<Row> getComparator();
}