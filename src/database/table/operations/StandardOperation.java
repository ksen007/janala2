/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package database.table.operations;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 8/22/12
 * Time: 1:31 AM
 */
public abstract class StandardOperation implements Operations {
    public int tableIndex;
    public String columnName;

    protected StandardOperation(int tableIndex, String columnName) {
        this.tableIndex = tableIndex;
        this.columnName = columnName;
    }
}
