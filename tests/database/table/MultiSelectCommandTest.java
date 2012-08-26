/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package database.table;

import database.table.commands.SelectCommand;
import database.table.from.From;
import database.table.groupby.DefaultGroupBy;
import database.table.having.HavingTrue;
import database.table.internals.ForeignKey;
import database.table.internals.Row;
import database.table.internals.Table;
import database.table.internals.TableFactory;
import database.table.operations.IdentityOperation;
import database.table.operations.StandardOperation;
import database.table.orderby.SimpleOrderBy;
import database.table.select.OperationSingleTableSelect;
import database.table.where.Where;
import junit.framework.TestCase;

import java.sql.ResultSet;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 8/26/12
 * Time: 9:50 AM
 */
public class MultiSelectCommandTest extends TestCase {
    private Table Customers;
    private Table Orders;
    private Table Publishers;
    private Table Books;

    public void setUp() throws Exception {
        Customers = TableFactory.create("Customers", new String[]{"Id",
                "Name", "PasswordHash", "Age"}, new int[]{Table.INT,
                Table.STRING, Table.INT, Table.INT}, new int[]{Table.PRIMARY,
                Table.NONE, Table.NONE, Table.NONE}, new ForeignKey[]{null, null, null,
                null});

        Orders = TableFactory.create("Orders", new String[] { "Id",
                "CustomerId", "OrderDateTime", "CancelDate", "BookId",
                "IsCanceled" }, new int[] { Table.INT, Table.INT,
                Table.INT, Table.INT, Table.INT, Table.INT },
                new int[] { Table.PRIMARY, Table.NONE, Table.NONE, Table.NONE, Table.NONE, Table.NONE },
                new ForeignKey[] { null, new ForeignKey(Customers, "Id"),
                        null, null, null, null });

        Publishers = TableFactory.create("Publishers", new String[] { "Id",
                "Name" }, new int[] { Table.INT, Table.STRING },
                new int[] { Table.PRIMARY, Table.NONE }, new ForeignKey[] { null,
                null });

        Books = TableFactory.create("Books", new String[] { "Id", "ISBN",
                "Title", "Price", "Year", "PublisherId", "Stock" },
                new int[] { Table.INT, Table.INT, Table.STRING, Table.INT,
                        Table.INT, Table.INT, Table.INT }, new int[] {Table.PRIMARY,
                Table.NONE, Table.NONE, Table.NONE, Table.NONE, Table.NONE, Table.NONE },
                new ForeignKey[] { null, null, null, null, null,
                        new ForeignKey(Publishers, "Id"), null });
        		//Customers
		Customers.insert(new Object[]{0, "Tanaka", 3,25});
        Customers.insert(new Object[]{1, "Suzuki", 3,21});
        Customers.insert(new Object[]{2, "Goto", 3,28});
        Customers.insert(new Object[]{3, "Honda", 3,30});
        Customers.insert(new Object[]{4, "Motohashi", 3,18});
        Customers.insert(new Object[]{5, "Matsumoto", 3,11});

		//Books
		Books.insert(new Object[]{20, 1234567890, "The Art of C++", 20, 1987, 20, 1});
        Books.insert(new Object[]{21, 1234567891, "The Art of C#", 25, 2000, 21, 1});
        Books.insert(new Object[]{22, 1234567892, "The Art of Lisp", 30, 1980, 20, 1});
        Books.insert(new Object[]{23, 1234567894, "Java HandBook", 5, 1999, 21, 1});

		//Publishers
        Publishers.insert(new Object[]{20, "Pearson Education"});
        Publishers.insert(new Object[]{21, "O Reilly"});

		//Orders
        Orders.insert(new Object[]{20, 1, 20120310, null, 20, 0});

    }

    public void tearDown() throws Exception {

    }

    public void testSelect1() throws Exception {
        Table t = (new SelectCommand(
                new OperationSingleTableSelect(new StandardOperation[]{new IdentityOperation("Id"),new IdentityOperation("Age")}),
                new From(new Table[]{Customers}),
                new Where() {
                    public boolean where(Row[] rows) {
                        Integer i = (Integer) rows[0].get("Id");
                        return (i!=null && i >= 3);
                    }
                },
                new DefaultGroupBy(),
                new HavingTrue(),
                new SimpleOrderBy(new String[]{"Age"},true),
                false
        )).execute();
        assertEquals(3,t.size());
        ResultSet rs = t.getResultSet();
        rs.next();
        assertEquals(5,rs.getInt("Id"));
        rs.next();
        assertEquals(4,rs.getInt("Id"));
        rs.next();
        assertEquals(3,rs.getInt("Id"));
    }

}
