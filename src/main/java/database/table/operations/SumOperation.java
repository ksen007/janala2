package database.table.operations;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 8/22/12
 * Time: 1:57 AM
 */
public class SumOperation extends StandardOperation {

  public SumOperation(int tableIndex, String columnName) {
    super(tableIndex, columnName);
  }

  public SumOperation(String columnName) {
    super(columnName);
  }

  @Override
  protected Object operation(Object aggregate, Object current) {
    Integer nu = (Integer) current;
    Integer ol = (Integer) aggregate;
    return nu + ol;
  }

  @Override
  public String name() {
    return "SUM(" + columnName + ")";
  }
}
