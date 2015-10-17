package database.table.groupby;

import database.table.internals.Row;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */
public class SimpleGroupBy implements GroupBy {
  private String[] columns;
  private int[] tableIndices;

  public SimpleGroupBy(String[] columns) {
    this.columns = columns;
    tableIndices = new int[columns.length];
    for (int i = 0; i < tableIndices.length; i++) {
      tableIndices[i] = 0;
    }
  }

  public SimpleGroupBy(int[] tableIndices, String[] columns) {
    this.columns = columns;
    this.tableIndices = tableIndices;
  }

  public Object[] groupBy(Row[] rows) {
    Object[] ret = new Object[columns.length];
    for (int i = 0; i < ret.length; i++) {
      ret[i] = rows[tableIndices[i]].get(columns[i]);
    }
    return ret;
  }
}
