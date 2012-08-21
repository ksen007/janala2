/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package database.table;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 8/22/12
 * Time: 2:00 AM
 */
public class CountOperation extends StandardOperation {
    private int tableIndex;

    public CountOperation(String columnName) {
        super(0,columnName);
    }

    public CountOperation(int tableIndex, String columnName) {
        super(tableIndex,columnName);
    }

    public Object apply(Object old, Row[] rows) {
            Object nu = rows[tableIndex].get(columnName);
            if (nu==null) return old;
            if (old==null) return 1;
            return (Integer)old+1;
    }
}
