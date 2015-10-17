package database.table.from;

import database.table.internals.Table;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */
public class From {
  private Table[] tables;

  public From(Table[] tables) {
    this.tables = tables;
  }

  public Table[] tables() {
    return tables;
  }
}
