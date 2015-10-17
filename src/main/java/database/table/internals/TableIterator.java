package database.table.internals;

import java.util.ListIterator;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 8/12/12
 * Time: 11:06 AM
 */
public class TableIterator {
  private ListIterator<Row> iter;

  public TableIterator(ListIterator<Row> iter) {
    this.iter = iter;
  }

  public boolean hasNext() {
    return iter.hasNext();
  }

  public Row next() {
    return iter.next();
  }

  public boolean hasPrevious() {
    return iter.hasPrevious();
  }

  public Row previous() {
    return iter.previous();
  }

  public int nextIndex() {
    return iter.nextIndex();
  }

  public int previousIndex() {
    return iter.previousIndex();
  }

  public void remove() {
    iter.remove();
  }

  public void set(Row row) {
    iter.set(row);
  }

  public void add(Row row) {
    iter.add(row);
  }
}
