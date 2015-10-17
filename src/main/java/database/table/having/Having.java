package database.table.having;

import database.table.internals.Row;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */
public interface Having {
  public boolean having(Row row);
}
