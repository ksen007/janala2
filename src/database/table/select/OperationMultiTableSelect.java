/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package database.table.select;

import database.table.operations.Operations;
import database.table.operations.StandardOperation;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */
public class OperationMultiTableSelect implements Select {
    private String[] columns;
    private StandardOperation[] operations;

    public OperationMultiTableSelect(String[] columnNames, StandardOperation[] operations) {
        this.operations = operations;
        this.columns = columnNames;
    }

    public String[] selectAs() {
        return columns;
    }

    public Operations[] select() {
        return operations;
    }
}

