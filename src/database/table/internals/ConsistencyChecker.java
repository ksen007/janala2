/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package database.table.internals;

public class ConsistencyChecker {

    public static void checkRow(Table table, Row row) {
        boolean[] primaries = table.getPrimaries();
        boolean[] uniques = table.getUniques();
        boolean[] nonnulls = table.getNonNulls();
        String[] columnNames = table.getColumnNames();
        ForeignKey[] foreignKeys = table.getForeignKeys();

        int cnt = 0;
        for (int j=0; j<columnNames.length; j++) {
            if (primaries != null && primaries[j])
                cnt++;
        }
        if (cnt > 0) {
            Object[] thisPrimary = new Object[cnt];
            int i = 0;
            for (int j=0; j<columnNames.length; j++) {
                if (primaries!=null && primaries[j]) {
                    thisPrimary[i] = row.get(columnNames[j]);
                    assert thisPrimary[i] != null;
                    i++;
                }
            }
            ArrayBasedTuple thisArray = new ArrayBasedTuple(thisPrimary);
            TableIterator iter = table.iterator();
            while (iter.hasNext()) {
                Row otherRow = iter.next();
                if (row!=otherRow) {
                    Object[] otherPrimary = new Object[cnt];
                    i = 0;
                    for (int j=0; j<columnNames.length; j++) {
                        if (primaries!=null && primaries[j]) {
                            otherPrimary[i] = otherRow.get(columnNames[j]);
                            assert otherPrimary[i] != null;
                            i++;
                        }
                    }
                    ArrayBasedTuple otherArray = new ArrayBasedTuple(otherPrimary);
                    assert (!thisArray.equals(otherArray));
                }
            }
        }
        for (int j=0; j<columnNames.length; j++) {
            if (uniques!=null && uniques[j]) {
                Object value = row.get(columnNames[j]);
                assert value != null;
                TableIterator iter = table.iterator();
                while (iter.hasNext()) {
                    Row otherRow = iter.next();
                    if (row!=otherRow)
                        assert (!value.equals(otherRow.get(columnNames[j])));
                }
            }
            if (nonnulls!=null && nonnulls[j]) {
                Object value = row.get(columnNames[j]);
                assert value != null;
            }
        }
        for (int j=0; j<columnNames.length; j++) {
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
