package database.table.select;

import database.table.operations.Operations;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 8/20/12
 * Time: 12:44 PM
 */
public interface Select {
  public String[] selectAs();

  public Operations[] select();
}
