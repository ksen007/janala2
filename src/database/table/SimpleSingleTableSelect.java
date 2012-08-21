/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package database.table;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */
public class SimpleSingleTableSelect implements Select {
    private String[] columns;

    public SimpleSingleTableSelect(String[] columns) {
        this.columns = columns;
    }

    public String[] selectAs() {
        return columns;
    }

    public Operations[] select() {
        Operations[] ret =  new Operations[columns.length];
        for (int i = 0; i < columns.length; i++) {
            ret[i] = new ProjectOperation(columns[i]);
        }
        return ret;
    }
}
