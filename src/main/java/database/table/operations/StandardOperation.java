package database.table.operations;

import database.table.internals.Row;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 8/22/12
 * Time: 1:31 AM
 */
public abstract class StandardOperation implements Operations {
  public int tableIndex;
  public String columnName;

  public StandardOperation(int tableIndex, String columnName) {
    this.tableIndex = tableIndex;
    this.columnName = columnName;
  }

  public StandardOperation(String columnName) {
    this.tableIndex = 0;
    this.columnName = columnName;
  }

  public Object apply(Object old, Row[] rows) {
    Object nu = rows[tableIndex].get(columnName);
    if (old == null) return nu;
    if (nu == null) return old;
    return operation(old, nu);
  }

  protected abstract Object operation(Object aggregate, Object current);

  public abstract String name();
}
