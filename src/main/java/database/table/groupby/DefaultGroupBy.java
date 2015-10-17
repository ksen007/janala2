package database.table.groupby;

import database.table.internals.Row;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */
public class DefaultGroupBy implements GroupBy {
  int i = 0;

  public Object[] groupBy(Row[] rows) {
    i++;
    return new Object[] {i};
  }
}
