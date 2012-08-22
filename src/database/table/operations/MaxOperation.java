/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package database.table.operations;

import database.table.internals.Row;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */
public class MaxOperation extends StandardOperation {
    private int tableIndex;

    public MaxOperation(String columnName) {
        super(0,columnName);
    }

    public MaxOperation(int tableIndex, String columnName) {
        super(tableIndex,columnName);
    }

    public Object apply(Object old, Row[] rows) {
        if (old==null)
            return rows[tableIndex].get(columnName);
        else {
            Comparable nu = (Comparable)rows[tableIndex].get(columnName);
            if (nu==null) return old;
            Comparable ol = (Comparable)old;
            return nu.compareTo(ol)>0? nu:ol;
        }
    }
}
