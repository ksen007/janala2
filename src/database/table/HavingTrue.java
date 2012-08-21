/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package database.table;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */
public class HavingTrue implements Having {
    public boolean having(Row row) {
        return true;
    }
}
