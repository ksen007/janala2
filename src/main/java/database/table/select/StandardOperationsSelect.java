package database.table.select;

import database.table.operations.Operations;
import database.table.operations.StandardOperation;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */
public class StandardOperationsSelect implements Select {
  String[] columns;
  StandardOperation[] operations;

  public String[] selectAs() {
    return columns;
  }

  public Operations[] select() {
    return operations;
  }
}
