package database.table;

import java.util.ListIterator;
import java.util.Map;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 7/2/12
 * Time: 12:32 PM
 */
public interface Table {
    void insert(String[] columns, Object[] values);

    void insert(Object[] values);

    ListIterator<Map<String,Object>> iterator();

    int delete(Where where);

    int update(Where where);

    Table select(Where where, String[][] selectColumns, TableImpl[] fromOther);
}
