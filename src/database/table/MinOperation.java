/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package database.table;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */
public class MinOperation extends StandardOperation {
    private int tableIndex;

    public MinOperation(String columnName) {
        super(0,columnName);
    }

    public MinOperation(int tableIndex, String columnName) {
        super(tableIndex,columnName);
    }

    public Object apply(Object old, Row[] rows) {
        if (old==null)
            return rows[tableIndex].get(columnName);
        else {
            Comparable nu = (Comparable)rows[tableIndex].get(columnName);
            if (nu==null) return old;
            Comparable ol = (Comparable)old;
            return nu.compareTo(ol)<0? nu:ol;
        }
    }
}
