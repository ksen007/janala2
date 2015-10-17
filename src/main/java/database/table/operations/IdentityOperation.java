package database.table.operations;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */
public class IdentityOperation extends StandardOperation {

  public IdentityOperation(int tableIndex, String columnName) {
    super(tableIndex, columnName);
  }

  public IdentityOperation(String columnName) {
    super(columnName);
  }

  @Override
  protected Object operation(Object aggregate, Object current) {
    return aggregate;
  }

  @Override
  public String name() {
    return columnName;
  }
}
