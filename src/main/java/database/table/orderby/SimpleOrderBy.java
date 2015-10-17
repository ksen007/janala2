package database.table.orderby;

import database.table.internals.Row;

import java.util.Comparator;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */
public class SimpleOrderBy implements OrderBy {
  Comparator comp;
  String[] columns;
  boolean isAscending;

  public SimpleOrderBy(String[] columnNames, boolean ascending) {
    this.columns = columnNames;
    isAscending = ascending;
    comp =
        new Comparator<Row>() {
          public int compare(Row r1, Row r2) {
            for (int i = 0; i < columns.length; i++) {
              String cname = columns[i];
              int cmp = ((Comparable) r1.get(cname)).compareTo((Comparable) r2.get(cname));
              if (cmp != 0) {
                if (!isAscending) return 0 - cmp;
                else return cmp;
              }
            }
            return 0;
          }
        };
  }

  public Comparator<Row> getComparator() {
    return comp;
  }
}
