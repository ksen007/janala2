/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package database.table;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 8/21/12
 * Time: 10:50 PM
 */
public class ProjectOperation implements Operations {
    private String columnName;

    public ProjectOperation(String columnName) {
        this.columnName = columnName;
    }

    public Object apply(Object old, Row[] rows) {
        return rows[0].get(columnName);
    }
}
