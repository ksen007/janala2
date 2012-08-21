/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package database.table;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 8/20/12
 * Time: 12:48 PM
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
