/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package database.table.select;

import database.table.operations.IdentityOperation;
import database.table.operations.Operations;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */
public class SimpleMultiTableSelect implements Select {
    private String[] columns;
    private int[] tableIndices;
    private String[] sourceColumns;

    public SimpleMultiTableSelect(String[] target, int[] tableIndices, String[] source) {
        if (target.length!=tableIndices.length || target.length!=source.length) {
            throw new RuntimeException("target, tableIndices, source must have same lengths");
        }
        this.columns = target;
        this.sourceColumns = source;
        this.tableIndices = tableIndices;
    }

    public String[] selectAs() {
        return columns;
    }

    public Operations[] select() {
        Operations[] ret =  new Operations[sourceColumns.length];
        for (int i = 0; i < sourceColumns.length; i++) {
            ret[i] = new IdentityOperation(tableIndices[i],sourceColumns[i]);
        }
        return ret;
    }
}
