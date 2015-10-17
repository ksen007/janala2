package database.table.select;

import database.table.operations.IdentityOperation;
import database.table.operations.StandardOperation;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */
public class SimpleMultiTableSelect extends StandardOperationsSelect {

  public SimpleMultiTableSelect(String[] target, int[] tableIndices, String[] source) {
    if (target.length != tableIndices.length || target.length != source.length) {
      throw new RuntimeException("target, tableIndices, source must have same lengths");
    }
    this.columns = target;
    this.operations = new StandardOperation[source.length];
    for (int i = 0; i < source.length; i++) {
      operations[i] = new IdentityOperation(tableIndices[i], source[i]);
    }
  }
}
