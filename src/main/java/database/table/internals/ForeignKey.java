package database.table.internals;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 7/9/12
 * Time: 12:40 PM
 */
public class ForeignKey {
  public Table table;
  public String key;

  public ForeignKey(Table table, String key) {
    this.table = table;
    this.key = key;
  }
}
