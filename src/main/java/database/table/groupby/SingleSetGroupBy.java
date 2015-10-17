package database.table.groupby;

import database.table.internals.Row;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */
public class SingleSetGroupBy implements GroupBy {
  public Object[] groupBy(Row[] rows) {
    return new Object[] {1};
  }
}
