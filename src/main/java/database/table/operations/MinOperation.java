package database.table.operations;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */
public class MinOperation extends StandardOperation {

  public MinOperation(int tableIndex, String columnName) {
    super(tableIndex, columnName);
  }

  public MinOperation(String columnName) {
    super(columnName);
  }

  @Override
  protected Object operation(Object aggregate, Object current) {
    Comparable nu = (Comparable) current;
    Comparable ol = (Comparable) aggregate;
    return nu.compareTo(ol) < 0 ? nu : ol;
  }

  @Override
  public String name() {
    return "MIN(" + columnName + ")";
  }
}
