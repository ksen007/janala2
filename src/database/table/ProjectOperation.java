/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package database.table;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */
public class ProjectOperation extends StandardOperation {
    private int tableIndex;

    public ProjectOperation(String columnName) {
        super(0,columnName);
        this.tableIndex = 0;
    }

    public ProjectOperation(int tableIndex, String columnName) {
        super(tableIndex,columnName);
    }

    public Object apply(Object old, Row[] rows) {
        return rows[tableIndex].get(columnName);
    }
}
