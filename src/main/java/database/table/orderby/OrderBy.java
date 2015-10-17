package database.table.orderby;

import database.table.internals.Row;

import java.util.Comparator;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */
public interface OrderBy {
  public Comparator<Row> getComparator();
}
