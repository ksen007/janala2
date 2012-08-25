/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package database.table.operations;

import database.table.internals.Row;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 8/22/12
 * Time: 2:00 AM
 */
public class CountOperation extends StandardOperation {

    public CountOperation(int tableIndex, String columnName) {
        super(tableIndex, columnName);
    }

    public CountOperation(String columnName) {
        super(columnName);
    }

    public Object apply(Object old, Row[] rows) {
        Object nu = rows[tableIndex].get(columnName);
        if (old==null && nu == null) return 0;
        if (old==null) return 1;
        if (nu==null) return old;
        return (Integer)old+1;
    }

    @Override
    protected Object operation(Object aggregate, Object current) {
        throw new RuntimeException("Should not be called.");
    }
}
