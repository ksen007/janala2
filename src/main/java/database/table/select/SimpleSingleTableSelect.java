package database.table.select;

import database.table.operations.IdentityOperation;
import database.table.operations.StandardOperation;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */
public class SimpleSingleTableSelect extends StandardOperationsSelect {

  public SimpleSingleTableSelect(String[] columns) {
    this.columns = columns;
    this.operations = new StandardOperation[columns.length];
    for (int i = 0; i < columns.length; i++) {
      this.operations[i] = new IdentityOperation(columns[i]);
    }
  }
}
