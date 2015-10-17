package database.table.having;

import database.table.internals.Row;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */
public class HavingTrue implements Having {
  public boolean having(Row row) {
    return true;
  }
}
