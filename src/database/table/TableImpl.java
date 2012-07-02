/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package database.table;

import java.util.*;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 7/2/12
 * Time: 10:18 AM
 */
public class TableImpl implements Table {
    private String name;
    private String[] columnNames;
    private List<Map<String,Object>> rows;

    public TableImpl(String name, String[] columnNames) {
        this.name = name;
        this.columnNames = columnNames;
        rows = new LinkedList<Map<String, Object>>();
    }

    public void insert(String[] columns, Object[] values) {
        assert(columns.length==values.length);
        Map<String,Object> row = new TreeMap<String, Object>();
        for (int i = 0; i < columns.length; i++) {
            String column = columns[i];
            row.put(column,values[i]);
        }
        rows.add(row);
    }

    public void insert(Object[] values) {
        assert(columnNames.length==values.length);
        Map<String,Object> row = new TreeMap<String, Object>();
        for (int i = 0; i < columnNames.length; i++) {
            String column = columnNames[i];
            row.put(column,values[i]);
        }
        rows.add(row);
    }

    public ListIterator<Map<String,Object>> iterator() {
        return rows.listIterator();
    }

    public int delete(Where where) {
        int ret=0;
        ListIterator<Map<String,Object>> iterator = rows.listIterator();
        Map<String,Object> [] tmp = new Map[1];
        while (iterator.hasNext()) {
            Map<String,Object> row = iterator.next();
            tmp[0] = row;
            if (where.isTrue(tmp)) {
                ret++;
                iterator.remove();
            }
        }
        return ret;
    }

    public int update(Where where) {
        int ret=0;
        for (Map<String, Object> row : rows) {
            if (where.modify(row)) {
                ret++;
            }
        }
        return ret;
    }

    public Table select(Where where, String[][] selectColumns, TableImpl[] fromOther) {
        int nTables = 1;
        if (fromOther!=null) {
            nTables += fromOther.length;
        }
        int nRows = 0;
        ArrayList<String> columns = new ArrayList<String>();
        for(int i=0 ; i<selectColumns.length; i++) {
            if (selectColumns[i]!=null) {
                nRows += selectColumns[i].length;
                String tableName;
                if (i==0) {
                    tableName = name;
                } else {
                    tableName = fromOther[i-1].name;
                }
                for (int j=0; j<selectColumns[i].length; j++) {
                    columns.add(tableName+"."+selectColumns[i][j]);
                }
            }
        }
        Table ret = TableFactory.create(null,(String[])columns.toArray());

        ListIterator<Map<String,Object>>[] iterators = new ListIterator[nTables];

        while (hasNext(iterators)) {
            Map<String,Object>[] rows = next(iterators, this, fromOther);
            if (where.isTrue(rows)) {
                ret.insert(doSelectColumns(nRows, selectColumns, rows));
            }
        }
        return ret;
    }

    private Object[] doSelectColumns(int nCols, String[][] selectColumns, Map<String, Object>[] rows) {
        Object[] ret = new Object[nCols];
        int k = 0;
        for(int i=0 ; i<selectColumns.length; i++) {
            if (selectColumns[i]!=null) {
                for (int j=0; j<selectColumns[i].length; j++) {
                    ret[k] = rows[i].get(selectColumns[i][j]);
                    k++;
                }
            }
        }
        return ret;
    }

    private static Map<String, Object>[] next(ListIterator<Map<String, Object>>[] iterators, TableImpl table, TableImpl[] fromOther) {
        Map<String,Object>[] ret = new Map[iterators.length];
        boolean first = true;
        for(int i=iterators.length-1; i>=0; i--) {
            if (iterators[i] == null || (first && !iterators[i].hasNext())) {
                if (i==0) {
                    iterators[i] = table.rows.listIterator();
                } else {
                    iterators[i] = fromOther[i-1].rows.listIterator();
                }
                ret[i] = iterators[i].next();
            } else if (first) {
                first = false;
                ret[i] = iterators[i].next();
            } else {
                iterators[i].previous();
                ret[i] = iterators[i].next();
            }
        }
        return ret;
    }

    private static boolean hasNext(ListIterator<Map<String, Object>>[] iterators) {
        for (int i=0; i<iterators.length;i++) {
            if (iterators[i].hasNext())
                return true;
        }
        return false;
    }
}
