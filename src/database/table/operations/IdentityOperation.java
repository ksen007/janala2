/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package database.table.operations;

import database.table.internals.Row;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */
public class IdentityOperation extends StandardOperation {
    private int tableIndex;

    public IdentityOperation(String columnName) {
        super(0,columnName);
        this.tableIndex = 0;
    }

    public IdentityOperation(int tableIndex, String columnName) {
        super(tableIndex,columnName);
    }

    public Object apply(Object old, Row[] rows) {
        return rows[tableIndex].get(columnName);
    }
}
