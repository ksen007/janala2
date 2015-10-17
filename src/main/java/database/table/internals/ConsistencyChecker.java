package database.table.internals;

import janala.Main;
import janala.interpreters.OrValue;

public class ConsistencyChecker {

  public static void checkRow(Table table, Row row) {
    boolean[] primaries = table.getPrimaries();
    boolean[] uniques = table.getUniques();
    boolean[] nonnulls = table.getNonNulls();
    String[] columnNames = table.getColumnNames();
    ForeignKey[] foreignKeys = table.getForeignKeys();

    int cnt = 0;
    for (int j = 0; j < columnNames.length; j++) {
      if (primaries != null && primaries[j]) cnt++;
    }
    if (cnt > 0) {
      Object[] thisPrimary = new Object[cnt];
      int i = 0;
      for (int j = 0; j < columnNames.length; j++) {
        if (primaries != null && primaries[j]) {
          thisPrimary[i] = row.get(columnNames[j]);
          assert thisPrimary[i] != null;
          i++;
        }
      }
      ArrayBasedTuple thisArray = new ArrayBasedTuple(thisPrimary);
      TableIterator iter = table.iterator();
      while (iter.hasNext()) {
        Row otherRow = iter.next();
        if (row != otherRow) {
          Object[] otherPrimary = new Object[cnt];
          i = 0;
          for (int j = 0; j < columnNames.length; j++) {
            if (primaries != null && primaries[j]) {
              otherPrimary[i] = otherRow.get(columnNames[j]);
              assert otherPrimary[i] != null;
              i++;
            }
          }
          ArrayBasedTuple otherArray = new ArrayBasedTuple(otherPrimary);
          assert (!thisArray.equals(otherArray));
        }
      }
    }
    for (int j = 0; j < columnNames.length; j++) {
      if (uniques != null && uniques[j]) {
        Object value = row.get(columnNames[j]);
        assert value != null;
        TableIterator iter = table.iterator();
        while (iter.hasNext()) {
          Row otherRow = iter.next();
          if (row != otherRow) assert (!value.equals(otherRow.get(columnNames[j])));
        }
      }
      if (nonnulls != null && nonnulls[j]) {
        Object value = row.get(columnNames[j]);
        assert value != null;
      }
    }
    for (int j = 0; j < columnNames.length; j++) {
      if (foreignKeys != null && foreignKeys[j] != null) {
        Object value = row.get(columnNames[j]);
        assert value != null;
        TableIterator iter = foreignKeys[j].table.iterator();
        boolean found = false;
        while (iter.hasNext()) {
          Row otherRow = iter.next();
          found = value.equals(otherRow.get(foreignKeys[j].key));
          if (found) break;
        }
        assert found;
      }
    }
  }

  public static void assumeConsistent(Table table) {
    TableIterator iter2 = table.iterator();
    while (iter2.hasNext()) {
      Row row = iter2.next();
      boolean[] primaries = table.getPrimaries();
      boolean[] uniques = table.getUniques();
      boolean[] nonnulls = table.getNonNulls();
      String[] columnNames = table.getColumnNames();
      ForeignKey[] foreignKeys = table.getForeignKeys();

      int cnt = 0;
      for (int j = 0; j < columnNames.length; j++) {
        if (primaries != null && primaries[j]) cnt++;
      }
      if (cnt > 0) {
        Object[] thisPrimary = new Object[cnt];
        int i = 0;
        for (int j = 0; j < columnNames.length; j++) {
          if (primaries != null && primaries[j]) {
            thisPrimary[i] = row.get(columnNames[j]);
            Main.Assume(thisPrimary[i] == null ? 0 : 1);
            i++;
          }
        }
        ArrayBasedTuple thisArray = new ArrayBasedTuple(thisPrimary);
        TableIterator iter = table.iterator();
        while (iter.hasNext()) {
          Row otherRow = iter.next();
          if (row != otherRow) {
            Object[] otherPrimary = new Object[cnt];
            i = 0;
            for (int j = 0; j < columnNames.length; j++) {
              if (primaries != null && primaries[j]) {
                otherPrimary[i] = otherRow.get(columnNames[j]);
                assert otherPrimary[i] != null;
                i++;
              }
            }
            ArrayBasedTuple otherArray = new ArrayBasedTuple(otherPrimary);
            Main.Assume(thisArray.equals(otherArray) ? 0 : 1);
          }
        }
      }
      for (int j = 0; j < columnNames.length; j++) {
        if (uniques != null && uniques[j]) {
          Object value = row.get(columnNames[j]);
          assert value != null;
          TableIterator iter = table.iterator();
          while (iter.hasNext()) {
            Row otherRow = iter.next();
            if (row != otherRow) Main.Assume(value.equals(otherRow.get(columnNames[j])) ? 0 : 1);
          }
        }
        if (nonnulls != null && nonnulls[j]) {
          Object value = row.get(columnNames[j]);
          assert value != null;
        }
      }
      for (int j = 0; j < columnNames.length; j++) {
        if (foreignKeys != null && foreignKeys[j] != null) {
          OrValue tmp = null;
          Object value = row.get(columnNames[j]);
          Main.Assume(value == null ? 0 : 1);
          TableIterator iter = foreignKeys[j].table.iterator();
          boolean found = false;
          while (iter.hasNext()) {
            Row otherRow = iter.next();
            if (tmp == null) {
              Main.Ignore();
              Main.AssumeOrBegin(value.equals(otherRow.get(foreignKeys[j].key)) ? 1 : 0);
            } else {
              Main.Ignore();
              Main.AssumeOr(value.equals(otherRow.get(foreignKeys[j].key)) ? 1 : 0, tmp);
            }
            if (found) break;
          }
          if (tmp != null) {
            Main.AssumeOrEnd(tmp);
          }
        }
      }
    }
  }
}
