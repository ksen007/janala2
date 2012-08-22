/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package database.table.operations;

import database.table.internals.Row;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 8/22/12
 * Time: 1:57 AM
 */
public class SumOperation extends StandardOperation {
    private int tableIndex;

    public SumOperation(String columnName) {
        super(0,columnName);
    }

    public SumOperation(int tableIndex, String columnName) {
        super(tableIndex,columnName);
    }

    public Object apply(Object old, Row[] rows) {
        if (old==null)
            return rows[tableIndex].get(columnName);
        else {
            Integer nu = (Integer)rows[tableIndex].get(columnName);
            if (nu==null) return old;
            Integer ol = (Integer)old;
            return nu+ol;
        }
    }
}
