package database.table.internals;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 7/2/12
 * Time: 12:33 PM
 */
public class TableFactory {
  private static int anonymousTableCount = 0;

  public static Table create(String[] columnNames) {
    anonymousTableCount++;
    return new TableImpl("InternalTable" + anonymousTableCount, columnNames);
  }

  public static Table create(
      String name,
      String[] columnNames,
      int[] columnTypes,
      int[] attributes,
      ForeignKey[] foreignKeys) {
    assert (columnNames.length == columnTypes.length
        && columnNames.length == attributes.length
        && columnNames.length == foreignKeys.length);
    boolean[] primaries = new boolean[attributes.length];
    boolean[] uniques = new boolean[attributes.length];
    boolean[] nonulls = new boolean[attributes.length];
    for (int i = 0; i < attributes.length; i++) {
      int attribute = attributes[i];
      primaries[i] = (attribute & Table.PRIMARY) != 0;
      uniques[i] = (attribute & Table.UNIQUE) != 0;
      nonulls[i] = (attribute & Table.NONNULL) != 0;
    }
    return new TableImpl(name, columnNames, columnTypes, primaries, uniques, nonulls, foreignKeys);
  }
}
