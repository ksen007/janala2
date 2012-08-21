/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package database.table;

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

