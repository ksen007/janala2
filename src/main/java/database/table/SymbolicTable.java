package database.table;

import database.table.internals.ForeignKey;
import database.table.internals.Row;
import database.table.internals.Table;
import database.table.internals.TableIterator;
import janala.Main;
import janala.interpreters.OrValue;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 7/2/12
 * Time: 11:47 PM
 */
public class SymbolicTable {

  public static void insertSymbolicRows(Table table, int n) {
    insertSymbolicRows(table, n, true);
  }

  public static void insertSymbolicRowsNoCheck(Table table, int n) {
    insertSymbolicRows(table, n, false);
  }

  private static void insertSymbolicRows(Table table, int n, boolean checkConsistency) {
    int[] types = table.getColumnTypes();
    boolean[] primaries = table.getPrimaries();
    String[] columnNames = table.getColumnNames();
    ForeignKey[] foreignKeys = table.getForeignKeys();
    for (int i = 0; i < n; i++) {
      Object[] row = new Object[types.length];
      System.out.print(table.getName() + " {");
      for (int j = 0; j < row.length; j++) {
        if (types[j] == Table.INT) {
          int x = Main.readInt(i);
          Main.MakeSymbolic(x);
          Integer k = new Integer(x);
          row[j] = k;
        } else if (types[j] == Table.LONG) {
          long x = Main.readLong(i);
          Main.MakeSymbolic(x);
          Long k = new Long(x);
          row[j] = k;
        } else if (types[j] == Table.DATE) {
          long x = Main.readLong(i + 1);
          Main.MakeSymbolic(x);
          Main.Assume(x > 0 ? 1 : 0);
          java.sql.Date k = new java.sql.Date(x);
          row[j] = k;
        } else if (types[j] == Table.TIME) {
          long x = Main.readLong(i + 1);
          Main.MakeSymbolic(x);
          Main.Assume(x > 0 ? 1 : 0);
          java.sql.Time k = new java.sql.Time(x);
          row[j] = k;
        } else if (types[j] == Table.TIMESTAMP) {
          long x = Main.readLong(i + 1);
          Main.MakeSymbolic(x);
          Main.Assume(x > 0 ? 1 : 0);
          java.sql.Timestamp k = new java.sql.Timestamp(x);
          row[j] = k;
        } else if (types[j] == Table.STRING) {
          String x = Main.readString(i + "");
          Main.MakeSymbolic(x);
          row[j] = x;
        }
        System.out.print(row[j]);
        System.out.print(" , ");

        if (checkConsistency) {
          // now assume primary key constraint
          if (primaries[j]) {
            TableIterator iter = table.iterator();
            while (iter.hasNext()) {
              Row otherRow = iter.next();
              Main.Assume(row[j].equals(otherRow.get(columnNames[j])) ? 0 : 1);
            }
          }

          // now assume foreign key constraints
          if (foreignKeys[j] != null) {
            OrValue tmp = null;
            TableIterator iter = foreignKeys[j].table.iterator();
            while (iter.hasNext()) {
              Row otherRow = iter.next();
              if (tmp == null) {
                Main.Ignore();
                tmp = Main.AssumeOrBegin(row[j].equals(otherRow.get(foreignKeys[j].key)) ? 1 : 0);
              } else {
                Main.Ignore();
                tmp = Main.AssumeOr(row[j].equals(otherRow.get(foreignKeys[j].key)) ? 1 : 0, tmp);
              }
            }
            if (tmp != null) {
              Main.AssumeOrEnd(tmp);
            }
          }
        }
      }
      System.out.println("}");

      if (checkConsistency) table.insert(row);
      else table.insertNoCheck(row);
    }
  }
}
