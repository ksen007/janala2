/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package database.table;

import java.util.Map;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 7/2/12
 * Time: 10:42 AM
 */
public interface Where {
    public boolean isTrue(Map<String,Object>[] rows);
    public boolean modify(Map<String,Object> row);

}
