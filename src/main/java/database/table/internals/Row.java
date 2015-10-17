package database.table.internals;

import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 8/12/12
 * Time: 10:52 AM
 */
public class Row extends TreeMap<String, Object> {
  @Override
  public boolean equals(Object o) {
    if (!(o instanceof TreeMap)) {
      return false;
    }
    TreeMap<String, Object> other = (TreeMap<String, Object>) o;
    TreeSet<String> tmp = new TreeSet<String>(keySet());
    tmp.addAll(other.keySet());
    for (String key : tmp) {
      Object value1 = get(key);
      Object value2 = other.get(key);
      if (value1 == null && value2 != null) return false;
      if (value2 == null && value1 != null) return false;
      if (value1 != value2 && !value1.equals(value2)) return false;
    }
    return true;
  }
}
