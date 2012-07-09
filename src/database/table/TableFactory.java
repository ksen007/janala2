/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package database.table;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 7/2/12
 * Time: 12:33 PM
 */
public class TableFactory {
    public static Table create(String name, String[] columnNames) {
        return new TableImpl(name,columnNames);
    }

    public static Table create(String name, String[] columnNames, int[] columnTypes, boolean [] primaryKeys, ForeignKey[] foreignKeys) {
        assert(columnNames.length==columnTypes.length && columnNames.length == primaryKeys.length && columnNames.length == foreignKeys.length);
        return new TableImpl(name,columnNames,columnTypes,primaryKeys,foreignKeys);
    }
}
