package database.table.operations;

import database.table.internals.Row;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */
public interface Operations {
  public Object apply(Object old, Row[] rows);
}
