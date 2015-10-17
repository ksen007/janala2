package database.table.internals;

import database.table.orderby.OrderBy;
import database.table.where.Where;

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
  final public static int DATE = 4;
  final public static int TIME = 5;
  final public static int TIMESTAMP = 6;
  int NONE = 0;
  int PRIMARY = 1;
  int UNIQUE = 2;
  int NONNULL = 4;

  public void insert(Row row);

  public void insert(String[] columns, Object[] values);

  public void insert(Object[] values);

  public void insertNoCheck(Object[] row);

  public TableIterator iterator();

  public int delete(Where where);

  public int update(Where where);

  public Table select(Where where, String[][] selectColumns, Table[] fromOther);

  public ResultSet getResultSet();

  public String getName();

  public String[] getColumnNames();

  public int[] getColumnTypes();

  public boolean[] getPrimaries();

  public boolean[] getUniques();

  public boolean[] getNonNulls();

  public ForeignKey[] getForeignKeys();

  public void orderBy(OrderBy orderBy);

  public int size();

  public Object value();

  public boolean in(Object o);

  public boolean any(Object o, Predicate p);

  public void setForeignKeys(ForeignKey[] fkeys);
}
