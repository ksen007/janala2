package database.table.where;

import database.table.internals.Row;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */
public class WhereTrue extends Where {
  @Override
  public boolean where(Row[] rows) {
    return true;
  }
}
