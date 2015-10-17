package database.table.groupby;

import database.table.internals.Row;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */
public interface GroupBy {
  public Object[] groupBy(Row[] rows);
}
