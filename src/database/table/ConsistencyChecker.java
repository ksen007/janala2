/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package database.table;

import java.util.ListIterator;

public class ConsistencyChecker {

    public static void checkRow(Table table, Row row) {
        boolean[] primaries = table.getPrimaries();
        String[] columnNames = table.getColumnNames();
        ForeignKey[] foreignKeys = table.getForeignKeys();

        for (int j=0; j<columnNames.length; j++) {
            if (primaries!=null && primaries[j]) {
                Object value = row.get(columnNames[j]);
                assert value != null;
                TableIterator iter = table.iterator();
                while (iter.hasNext()) {
                    Row otherRow = iter.next();
                    if (row!=otherRow)
                        assert (!value.equals(otherRow.get(columnNames[j])));
                }
            }
            if (foreignKeys!=null && foreignKeys[j]!=null) {
                Object value = row.get(columnNames[j]);
                assert value != null;
                TableIterator iter = foreignKeys[j].table.iterator();
                boolean found = false;
                while (iter.hasNext()) {
                    Row otherRow = iter.next();
                    found = value.equals(otherRow.get(foreignKeys[j].key));
                    if (found) break;
                }
                assert found;
            }

        }

    }


}
