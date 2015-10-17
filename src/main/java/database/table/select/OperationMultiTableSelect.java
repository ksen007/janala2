package database.table.select;

import database.table.operations.StandardOperation;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */
public class OperationMultiTableSelect extends StandardOperationsSelect {

  public OperationMultiTableSelect(String[] columnNames, StandardOperation[] operations) {
    this.operations = operations;
    this.columns = columnNames;
  }
}
