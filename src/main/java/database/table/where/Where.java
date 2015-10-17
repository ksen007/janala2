package database.table.where;

import database.table.internals.Row;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 7/2/12
 * Time: 10:42 AM
 */
public abstract class Where {
  public boolean modify(Row row) {
    throw new RuntimeException("Unimplemented modify in Where class");
  }

  public abstract boolean where(Row[] rows);
}
