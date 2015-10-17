package database.table.select;

import database.table.internals.Table;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 8/27/12
 * Time: 8:35 AM
 */
public class SelectStar extends SimpleSingleTableSelect {
  public SelectStar(Table table) {
    super(table.getColumnNames());
  }
}
