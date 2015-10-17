package database.table.commands;

import database.table.from.From;
import database.table.groupby.GroupBy;
import database.table.having.Having;
import database.table.internals.*;
import database.table.operations.Operations;
import database.table.orderby.OrderBy;
import database.table.select.Select;
import database.table.where.Where;

import java.util.LinkedList;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */
public class SelectCommand {
  private Select select;
  private From from;
  private Where where;
  private GroupBy groupBy;
  private Having having;
  private OrderBy orderBy;
  private boolean isDistinct;

  public SelectCommand(
      Select select,
      From from,
      Where where,
      GroupBy groupBy,
      Having having,
      OrderBy orderBy,
      boolean distinct) {
    this.select = select;
    this.from = from;
    this.where = where;
    this.groupBy = groupBy;
    this.having = having;
    this.orderBy = orderBy;
    isDistinct = distinct;
  }

  class Pair {
    ArrayBasedTuple tuple;
    Row row;

    Pair(ArrayBasedTuple tuple, Row row) {
      this.tuple = tuple;
      this.row = row;
    }
  }

  public Table execute() {

    Table[] tables = from.tables();
    int nTables = tables.length;

    String[] selectAs = select.selectAs();
    Table ret = TableFactory.create(selectAs);

    TableIterator[] iterators = new TableIterator[nTables];
    LinkedList<Pair> groups = new LinkedList<Pair>();

    while (hasNext(iterators)) {
      boolean insert = false;
      Row[] rows = next(iterators, tables);
      if (where.where(rows)) {
        ArrayBasedTuple group = new ArrayBasedTuple(groupBy.groupBy(rows));
        Row row = null;
        for (Pair tmp : groups) {
          if (tmp.tuple.equals(group)) {
            row = tmp.row;
            break;
          }
        }
        if (row == null) {
          row = new Row();
          insert = true;
          groups.add(new Pair(group, row));
        }
        Operations[] operations = select.select();
        int i = 0;
        if (selectAs.length != operations.length) {
          throw new RuntimeException(
              "In Select selectAs and select returns different length arrays");
        }
        for (String columnName : selectAs) {
          row.put(columnName, operations[i].apply(row.get(columnName), rows));
          i++;
        }
        if (insert && having.having(row)) {
          boolean exists = false;
          if (isDistinct) {
            TableIterator iter = ret.iterator();
            while (iter.hasNext()) {
              Row next = iter.next();
              if (next.equals(row)) {
                exists = true;
              }
            }
          }
          if (!exists) {
            ret.insert(row);
          }
        }
      }
    }
    if (orderBy != null) {
      ret.orderBy(orderBy);
    }
    return ret;
  }

  private static Row[] next(TableIterator[] iterators, Table[] tables) {
    Row[] ret = new Row[iterators.length];
    boolean first = true;
    for (int i = iterators.length - 1; i >= 0; i--) {
      if (iterators[i] == null || (first && !iterators[i].hasNext())) {
        iterators[i] = tables[i].iterator();
        ret[i] = iterators[i].next();
      } else if (first) {
        first = false;
        ret[i] = iterators[i].next();
      } else {
        iterators[i].previous();
        ret[i] = iterators[i].next();
      }
    }
    return ret;
  }

  private static boolean hasNext(TableIterator[] iterators) {
    for (int i = 0; i < iterators.length; i++) {
      if (iterators[i] == null || iterators[i].hasNext()) return true;
    }
    return false;
  }
}
