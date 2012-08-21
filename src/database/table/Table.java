/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package database.table;

import java.sql.ResultSet;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 7/2/12
 * Time: 12:32 PM
 */
public interface Table {
    final public static int INT = 1;
    final public static int STRING = 2;
    final public static int LONG = 3;

    public void insert(Row row);

    public void insert(String[] columns, Object[] values);

    public void insert(Object[] values);

    public TableIterator iterator();

    public int delete(Where where);

    public int update(Where where);

    public Table select(Where where, String[][] selectColumns, Table[] fromOther);

    public ResultSet getResultSet();

    public String getName();

    public String[] getColumnNames();

    public int[] getColumnTypes();

    public boolean[] getPrimaries();

    public ForeignKey[] getForeignKeys();

    public void orderBy(OrderBy orderBy);

    public int size();
}
