package database.table.internals;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */
public interface Predicate {
  public boolean predicate(Object o1, Object o2);
}
