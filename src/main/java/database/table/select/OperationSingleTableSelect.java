package database.table.select;

import database.table.operations.StandardOperation;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 8/22/12
 * Time: 1:34 AM
 */
public class OperationSingleTableSelect extends StandardOperationsSelect {

  public OperationSingleTableSelect(StandardOperation[] operations) {
    this.operations = operations;
    this.columns = new String[operations.length];
    for (int i = 0; i < operations.length; i++) {
      StandardOperation operation = operations[i];
      columns[i] = operation.name();
    }
  }
}
