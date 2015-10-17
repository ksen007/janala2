package database.table.internals;

import database.table.orderby.OrderBy;
import database.table.where.Where;

import java.sql.ResultSet;
import java.util.*;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 7/2/12
 * Time: 10:18 AM
 */
public class TableImpl implements Table {
  private String name;
  private String[] columnNames;
  private int[] columnTypes;
  private boolean[] isPrimary;
  private boolean[] isUnique;
  private boolean[] isNonNull;
  private ForeignKey[] foreignKeys;
  private List<Row> rows;

  public TableImpl(String name, String[] columnNames) {
    this.name = name;
    this.columnNames = columnNames;
    rows = new LinkedList<Row>();
  }

  public TableImpl(
      String name,
      String[] columnNames,
      int[] columnTypes,
      boolean[] primary,
      boolean[] unique,
      boolean[] nonNull,
      ForeignKey[] foreignKeys) {
    this.name = name;
    this.columnNames = columnNames;
    this.columnTypes = columnTypes;
    this.foreignKeys = foreignKeys;
    this.isPrimary = primary;
    this.isUnique = unique;
    this.isNonNull = nonNull;
    rows = new LinkedList<Row>();
  }

  public void insert(Row row) {
    ConsistencyChecker.checkRow(this, row);
    rows.add(row);
  }

  public void insert(String[] columns, Object[] values) {
    assert (columns.length == values.length);
    Row row = new Row();
    for (int i = 0; i < columns.length; i++) {
      String column = columns[i];
      row.put(column, values[i]);
    }
    ConsistencyChecker.checkRow(this, row);
    rows.add(row);
  }

  public void insert(Object[] values) {
    assert (columnNames.length == values.length);
    Row row = new Row();
    for (int i = 0; i < columnNames.length; i++) {
      String column = columnNames[i];
      row.put(column, values[i]);
    }
    ConsistencyChecker.checkRow(this, row);
    rows.add(row);
  }

  public void insertNoCheck(Object[] values) {
    assert (columnNames.length == values.length);
    Row row = new Row();
    for (int i = 0; i < columnNames.length; i++) {
      String column = columnNames[i];
      row.put(column, values[i]);
    }
    rows.add(row);
  }

  public TableIterator iterator() {
    return new TableIterator(rows.listIterator());
  }

  public int delete(Where where) {
    int ret = 0;
    TableIterator iterator = new TableIterator(rows.listIterator());
    Row[] tmp = new Row[1];
    while (iterator.hasNext()) {
      Row row = iterator.next();
      tmp[0] = row;
      if (where.where(tmp)) {
        ret++;
        iterator.remove();
      }
    }
    return ret;
  }

  public int update(Where where) {
    int ret = 0;
    for (Row row : rows) {
      if (where.modify(row)) {
        ConsistencyChecker.checkRow(this, row);
        ret++;
      }
    }
    return ret;
  }

  public Table select(Where where, String[][] selectColumns, Table[] fromOther) {
    int nTables = 1;
    if (fromOther != null) {
      nTables += fromOther.length;
    }
    int nRows = 0;
    ArrayList<String> columns = new ArrayList<String>();
    for (int i = 0; i < selectColumns.length; i++) {
      if (selectColumns[i] != null) {
        nRows += selectColumns[i].length;
        for (int j = 0; j < selectColumns[i].length; j++) {
          if (columns.contains(selectColumns[i][j])) {
            throw new RuntimeException("Duplicate column name in select " + selectColumns[i][j]);
          }
          columns.add(selectColumns[i][j]);
        }
      }
    }
    String[] tmp = new String[columns.size()];
    int l = 0;
    for (String next : columns) {
      tmp[l] = next;
      l++;
    }
    Table ret = TableFactory.create(tmp);

    TableIterator[] iterators = new TableIterator[nTables];

    while (hasNext(iterators)) {
      Row[] rows = next(iterators, this, fromOther);
      if (where.where(rows)) {
        ret.insert(doSelectColumns(nRows, selectColumns, rows));
      }
    }
    return ret;
  }

  public ResultSet getResultSet() {
    return new ResultSetImpl(new TableIterator(rows.listIterator()));
  }

  public String getName() {
    return name;
  }

  public String[] getColumnNames() {
    return columnNames;
  }

  public int[] getColumnTypes() {
    return columnTypes;
  }

  public boolean[] getPrimaries() {
    return isPrimary;
  }

  public boolean[] getUniques() {
    return isUnique;
  }

  public boolean[] getNonNulls() {
    return isNonNull;
  }

  public ForeignKey[] getForeignKeys() {
    return foreignKeys;
  }

  public void orderBy(OrderBy orderBy) {
    Collections.sort(rows, orderBy.getComparator());
  }

  public int size() {
    return rows.size();
  }

  public Object value() {
    if (columnNames.length != 1) {
      throw new RuntimeException("Table " + name + " must have one column.");
    }
    if (rows.size() != 1) {
      throw new RuntimeException("Table " + name + " must have one row.");
    }
    return rows.get(0).get(columnNames[0]);
  }

  public boolean in(Object o) {
    return any(
        o,
        new Predicate() {

          public boolean predicate(Object o, Object value) {
            if (o == null) {
              if (value == null) return true;
            } else {
              if (o.equals(value)) return true;
            }
            return false; //To change body of implemented methods use File | Settings | File Templates.
          }
        });
  }

  public boolean any(Object o, Predicate p) {
    if (columnNames.length != 1) {
      throw new RuntimeException("Table " + name + " must have one column.");
    }

    TableIterator iter = iterator();
    while (iter.hasNext()) {
      Row row = iter.next();
      Object value = row.get(columnNames[0]);
      if (p.predicate(o, value)) return true;
    }
    return false;
  }

  public void setForeignKeys(ForeignKey[] fkeys) {
    this.foreignKeys = fkeys;
  }

  private Object[] doSelectColumns(int nCols, String[][] selectColumns, Row[] rows) {
    Object[] ret = new Object[nCols];
    int k = 0;
    for (int i = 0; i < selectColumns.length; i++) {
      if (selectColumns[i] != null) {
        for (int j = 0; j < selectColumns[i].length; j++) {
          ret[k] = rows[i].get(selectColumns[i][j]);
          k++;
        }
      }
    }
    return ret;
  }

  private static Row[] next(TableIterator[] iterators, Table table, Table[] fromOther) {
    Row[] ret = new Row[iterators.length];
    boolean first = true;
    for (int i = iterators.length - 1; i >= 0; i--) {
      if (iterators[i] == null || (first && !iterators[i].hasNext())) {
        if (i == 0) {
          iterators[i] = table.iterator();
        } else {
          iterators[i] = fromOther[i - 1].iterator();
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

  private static boolean hasNext(TableIterator[] iterators) {
    for (int i = 0; i < iterators.length; i++) {
      if (iterators[i] == null || iterators[i].hasNext()) return true;
    }
    return false;
  }
}
