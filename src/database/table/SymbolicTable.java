/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package database.table;

import janala.Main;

import java.util.ListIterator;
import java.util.Map;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 7/2/12
 * Time: 11:47 PM
 */
public class SymbolicTable {

    public static void insertSymbolicRows(Table table, int n) {
        int[] types = table.getColumnTypes();
        boolean[] primaries = table.getPrimaries();
        String[] columnNames = table.getColumnNames();
        for (int i=0; i<n; i++) {
            Object[] row = new Object[types.length];
            for (int j=0; j<row.length; j++) {
                if (types[j]==Table.INT) {
                    int x = Main.readInt(0);
                    Main.MakeSymbolic(x);
                    Integer k = new Integer(x);
                    row[j] = k;
                } else if (types[j]==Table.LONG) {
                    long x = Main.readLong(0);
                    Main.MakeSymbolic(x);
                    Long k = new Long(x);
                    row[j] = k;
                } else if (types[j]==Table.STRING) {
                    String x = Main.readString("");
                    Main.MakeSymbolic(x);
                    row[j] = x;
                }

                // now assume primary key constraint
                if (primaries[j]) {
                    ListIterator<Map<String,Object>> iter = table.iterator();
                    while (iter.hasNext()) {
                        Map<String,Object> otherRow = iter.next();
                        Main.Assume(row[j].equals(otherRow.get(columnNames[j]))?0:1);
                    }
                }
            }
            table.insert(row);
        }
    }
}
