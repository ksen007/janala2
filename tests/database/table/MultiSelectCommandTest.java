/*
 * Copyright (c) 2012, NTT Multimedia Communications Laboratories, Inc. and Koushik Sen
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package database.table;

import database.table.commands.SelectCommand;
import database.table.from.From;
import database.table.groupby.DefaultGroupBy;
import database.table.groupby.SimpleGroupBy;
import database.table.groupby.SingleSetGroupBy;
import database.table.having.Having;
import database.table.having.HavingTrue;
import database.table.internals.*;
import database.table.operations.*;
import database.table.orderby.SimpleOrderBy;
import database.table.select.*;
import database.table.where.Where;
import database.table.where.WhereTrue;
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
    }

    public void tearDown() throws Exception {

    }

    public void testOrderBy() throws Exception {
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

        Table t = (new SelectCommand(
                new SimpleSingleTableSelect(new String[]{"Id","Age"}),
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
        assertEquals(5, rs.getInt("Id"));
        rs.next();
        assertEquals(4, rs.getInt("Id"));
        rs.next();
        assertEquals(3,rs.getInt("Id"));
    }

    public void testSimpleDistinct() throws Exception {
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
        Customers.insert(new Object[]{2, "Goto", 3,30});
        Customers.insert(new Object[]{3, "Honda", 3,30});
        Customers.insert(new Object[]{4, "Motohashi", 3,18});
        Customers.insert(new Object[]{5, "Matsumoto", 3,18});

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

        Table t = (new SelectCommand(
                new SimpleSingleTableSelect(new String[]{"Age"}),
                new From(new Table[]{Customers}),
                new WhereTrue(),
                new DefaultGroupBy(),
                new HavingTrue(),
                null,
                true  /* true means distinct */
        )).execute();
        TableIterator iter = t.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
        assertEquals(4,t.size());
        ResultSet rs = t.getResultSet();
        rs.next();
        assertEquals(25,rs.getInt("Age"));
        rs.next();
        assertEquals(21,rs.getInt("Age"));
        rs.next();
        assertEquals(30,rs.getInt("Age"));
        rs.next();
        assertEquals(18,rs.getInt("Age"));
    }


    public void testSimpleGroupByHaving() throws Exception {
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
        Customers.insert(new Object[]{2, "Goto", 3,30});
        Customers.insert(new Object[]{3, "Honda", 3,30});
        Customers.insert(new Object[]{4, "Motohashi", 3,18});
        Customers.insert(new Object[]{5, "Matsumoto", 3,18});

		//Books
		Books.insert(new Object[]{20, 1234567890, "The Art of C++", 20, 1987, 20, 1});
        Books.insert(new Object[]{21, 1234567891, "The Art of C#", 25, 2000, 21, 1});
        Books.insert(new Object[]{22, 1234567892, "The Art of Lisp", 30, 1980, 20, 1});
        Books.insert(new Object[]{23, 1234567894, "Java HandBook", 5, 1999, 21, 1});

		//Publishers
        Publishers.insert(new Object[]{20, "Pearson Education"});
        Publishers.insert(new Object[]{21, "O Reilly"});

		//Orders
//        Orders.insert(new Object[]{20, 1, 20120310, null, 20, 0});
        Orders.insert(new Object[]{1, 1, 20120310, null, 20, 0});
        Orders.insert(new Object[]{2, 2, 20120310, null, 20, 0});
        Orders.insert(new Object[]{3, 2, 20120310, null, 22, 0});
        Orders.insert(new Object[]{4, 2, 20120310, null, 23, 0});
        Orders.insert(new Object[]{5, 4, 20120310, null, 23, 0});

//        ResultSet rs = statement.executeQuery("select CustomerId, count() from Orders, Customers where CustomerId = Customers.Id group by CustomerId having Age >= 20");

        Table t = (new SelectCommand(
                new OperationMultiTableSelect(new String[]{"CustomerId","Age","count"},
                        new StandardOperation[]{new IdentityOperation(0,"CustomerId"), new IdentityOperation(1,"Age"), new CountOperation(0,"CustomerId")}),
                new From(new Table[]{Orders,Customers}),
                new Where() {

                    @Override
                    public boolean where(Row[] rows) {
                        return rows[0].get("CustomerId").equals(rows[1].get("Id"));
                    }
                },
                new SimpleGroupBy(new int[]{0},new String[]{"CustomerId"}),
                new Having() {

                    public boolean having(Row row) {
                        Integer i = (Integer) row.get("Age");
                        return (i!=null && i >= 20);
                    }
                },
                null,
                false  /* true means distinct */
        )).execute();
        TableIterator iter = t.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
        assertEquals(2,t.size());
        ResultSet rs = t.getResultSet();
        rs.next();
        assertEquals(21, rs.getInt("Age"));
        assertEquals(1, rs.getInt("CustomerId"));
        assertEquals(1, rs.getInt("count"));
        rs.next();
        rs.next();
        assertEquals(30, rs.getInt("Age"));
        assertEquals(2, rs.getInt("CustomerId"));
        assertEquals(3, rs.getInt("count"));
    }

    public void testSimpleGroupBy() throws Exception {
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
        Customers.insert(new Object[]{2, "Goto", 3,30});
        Customers.insert(new Object[]{3, "Honda", 3,30});
        Customers.insert(new Object[]{4, "Motohashi", 3,18});
        Customers.insert(new Object[]{5, "Matsumoto", 3,18});

		//Books
		Books.insert(new Object[]{20, 1234567890, "The Art of C++", 20, 1987, 20, 1});
        Books.insert(new Object[]{21, 1234567891, "The Art of C#", 25, 2000, 21, 1});
        Books.insert(new Object[]{22, 1234567892, "The Art of Lisp", 30, 1980, 20, 1});
        Books.insert(new Object[]{23, 1234567894, "Java HandBook", 5, 1999, 21, 1});

		//Publishers
        Publishers.insert(new Object[]{20, "Pearson Education"});
        Publishers.insert(new Object[]{21, "O Reilly"});

		//Orders
//        Orders.insert(new Object[]{20, 1, 20120310, null, 20, 0});
        Orders.insert(new Object[]{1, 1, 20120310, null, 20, 0});
        Orders.insert(new Object[]{2, 2, 20120310, null, 20, 0});
        Orders.insert(new Object[]{3, 2, 20120310, null, 22, 0});
        Orders.insert(new Object[]{4, 2, 20120310, null, 23, 0});
        Orders.insert(new Object[]{5, 4, 20120310, null, 23, 0});

// 			ResultSet rs = statement.executeQuery("select CustomerId, count() from Orders group by CustomerId");
        Table t = (new SelectCommand(
                new OperationSingleTableSelect(new StandardOperation[]{new IdentityOperation("CustomerId"), new CountOperation()}),
                new From(new Table[]{Orders}),
                new WhereTrue(),
                new SimpleGroupBy(new String[]{"CustomerId"}),
                new HavingTrue(),
                null,
                false  /* true means distinct */
        )).execute();
        TableIterator iter = t.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
        assertEquals(3,t.size());
        ResultSet rs = t.getResultSet();
        rs.next();
        assertEquals(1, rs.getInt("CustomerId"));
        assertEquals(1, rs.getInt("COUNT(*)"));
        rs.next();
        assertEquals(2, rs.getInt("CustomerId"));
        assertEquals(3, rs.getInt("COUNT(*)"));
        rs.next();
        assertEquals(4, rs.getInt("CustomerId"));
        assertEquals(1, rs.getInt("COUNT(*)"));
    }

    public void testSimpleMax() throws Exception {
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
        Customers.insert(new Object[]{2, "Goto", 3,30});
        Customers.insert(new Object[]{3, "Honda", 3,30});
        Customers.insert(new Object[]{4, "Motohashi", 3,18});
        Customers.insert(new Object[]{5, "Matsumoto", 3,18});

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

        Table t = (new SelectCommand(
                new OperationSingleTableSelect(new StandardOperation[]{new MaxOperation("Age")}),
                new From(new Table[]{Customers}),
                new WhereTrue(),
                new SingleSetGroupBy(),
                new HavingTrue(),
                null,
                false  /* true means distinct */
        )).execute();
        assertEquals(1,t.size());
        ResultSet rs = t.getResultSet();
        rs.next();
        assertEquals(30,rs.getInt("MAX(Age)"));
    }

    public void testSimpleView() throws Exception {
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
        Customers.insert(new Object[]{2, "Goto", 3,30});
        Customers.insert(new Object[]{3, "Honda", 3,30});
        Customers.insert(new Object[]{4, "Motohashi", 3,18});
        Customers.insert(new Object[]{5, "Matsumoto", 3,18});

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

//			statement.executeUpdate("create view Over20 as select * from Customers where Age >= 20");

        Table Over20 = (new SelectCommand(
                new SelectStar(Customers),
                new From(new Table[]{Customers}),
                new Where() {

                    @Override
                    public boolean where(Row[] rows) {
                        Integer i = (Integer) rows[0].get("Age");
                        return (i!=null && i >= 20);
                    }
                },
                new DefaultGroupBy(),
                new HavingTrue(),
                null,
                false  /* true means distinct */
        )).execute();

//        ResultSet rs = statement.executeQuery("select Id from Over20");
        Table t = (new SelectCommand(
                new SimpleSingleTableSelect(new String[]{"Id"}),
                new From(new Table[]{Over20}),
                new WhereTrue(),
                new DefaultGroupBy(),
                new HavingTrue(),
                null,
                false  /* true means distinct */
        )).execute();

        assertEquals(4,t.size());
        ResultSet rs = t.getResultSet();
        rs.next();
        assertEquals(0,rs.getInt("Id"));
        rs.next();
        assertEquals(1,rs.getInt("Id"));
        rs.next();
        assertEquals(2,rs.getInt("Id"));
        rs.next();
        assertEquals(3,rs.getInt("Id"));
    }

    public void testSimpleSelfJoin() throws Exception {
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
        Customers.insert(new Object[]{2, "Goto", 3,30});
        Customers.insert(new Object[]{3, "Honda", 3,30});
        Customers.insert(new Object[]{4, "Motohashi", 3,18});
        Customers.insert(new Object[]{5, "Matsumoto", 3,18});

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

//        ResultSet rs = statement.executeQuery("select A.Id from Customers A, Customers B where A.Age = B.Age and A.Id <> B.Id");

        Table t = (new SelectCommand(
                new SimpleMultiTableSelect(new String[]{"Id"},new int[]{0}, new String[]{"Id"}),
                new From(new Table[]{Customers, Customers}),
                new Where() {

                    @Override
                    public boolean where(Row[] rows) {
                        return rows[0].get("Age").equals(rows[1].get("Age")) && !rows[0].get("Id").equals(rows[1].get("Id"));
                    }
                },
                new DefaultGroupBy(),
                new HavingTrue(),
                null,
                false  /* true means distinct */
        )).execute();

        assertEquals(4,t.size());
        ResultSet rs = t.getResultSet();
        rs.next();
        assertEquals(2,rs.getInt("Id"));
        rs.next();
        assertEquals(3,rs.getInt("Id"));
        rs.next();
        assertEquals(4,rs.getInt("Id"));
        rs.next();
        assertEquals(5,rs.getInt("Id"));
    }


    public void testSimpleSum() throws Exception {
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
        Customers.insert(new Object[]{2, "Goto", 3,30});
        Customers.insert(new Object[]{3, "Honda", 3,30});
        Customers.insert(new Object[]{4, "Motohashi", 3,18});
        Customers.insert(new Object[]{5, "Matsumoto", 3,18});

		//Books
		Books.insert(new Object[]{20, 1234567890, "The Art of C++", 20, 1987, 20, 1});
        Books.insert(new Object[]{21, 1234567891, "The Art of C#", 25, 2000, 21, 1});
        Books.insert(new Object[]{22, 1234567892, "The Art of Lisp", 30, 1980, 20, 1});
        Books.insert(new Object[]{23, 1234567894, "Java HandBook", 5, 1999, 21, 1});

		//Publishers
        Publishers.insert(new Object[]{20, "Pearson Education"});
        Publishers.insert(new Object[]{21, "O Reilly"});

		//Orders
//        Orders.insert(new Object[]{20, 1, 20120310, null, 20, 0});
        Orders.insert(new Object[]{1, 1, 20120310, null, 20, 0});
        Orders.insert(new Object[]{2, 2, 20120310, null, 20, 0});
        Orders.insert(new Object[]{3, 2, 20120310, null, 22, 0});
        Orders.insert(new Object[]{4, 2, 20120310, null, 23, 0});
        Orders.insert(new Object[]{5, 4, 20120310, null, 23, 0});

        // 			ResultSet rs = statement.executeQuery("select sum(Age) from Customers");
        Table t = (new SelectCommand(
                new OperationSingleTableSelect(new StandardOperation[]{new SumOperation("Age")}),
                new From(new Table[]{Customers}),
                new WhereTrue(),
                new SingleSetGroupBy(),
                new HavingTrue(),
                null,
                false  /* true means distinct */
        )).execute();
        assertEquals(1,t.size());
        ResultSet rs = t.getResultSet();
        rs.next();
        assertEquals(142, rs.getInt("SUM(Age)"));
    }

    public void testSimpleSubquery_MultipleRecord() throws Exception {
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
        Customers.insert(new Object[]{2, "Goto", 3,30});
        Customers.insert(new Object[]{3, "Honda", 3,30});
        Customers.insert(new Object[]{4, "Motohashi", 3,18});
        Customers.insert(new Object[]{5, "Matsumoto", 3,18});

		//Books
		Books.insert(new Object[]{20, 1234567890, "The Art of C++", 20, 1987, 20, 1});
        Books.insert(new Object[]{21, 1234567891, "The Art of C#", 25, 2000, 21, 1});
        Books.insert(new Object[]{22, 1234567892, "The Art of Lisp", 30, 1980, 20, 1});
        Books.insert(new Object[]{23, 1234567894, "Java HandBook", 5, 1999, 21, 1});

		//Publishers
        Publishers.insert(new Object[]{20, "Pearson Education"});
        Publishers.insert(new Object[]{21, "O Reilly"});

		//Orders
//        Orders.insert(new Object[]{20, 1, 20120310, null, 20, 0});
        Orders.insert(new Object[]{1, 1, 20120310, null, 20, 0});
        Orders.insert(new Object[]{2, 2, 20120310, null, 20, 0});
        Orders.insert(new Object[]{3, 2, 20120310, null, 22, 0});
        Orders.insert(new Object[]{4, 2, 20120310, null, 23, 0});
        Orders.insert(new Object[]{5, 4, 20120310, null, 23, 0});

//        ResultSet rs = statement.executeQuery("select * from Orders where customerId = (select id from Customers where name == \"Goto\" )");
        final Table subTable = (new SelectCommand(
                new SimpleSingleTableSelect(new String[]{"Id"}),
                new From(new Table[]{Customers}),
                new Where() {

                    @Override
                    public boolean where(Row[] rows) {
                        String name = (String)rows[0].get("Name");
                        return (name!=null && name.equals("Goto"));
                    }
                },
                new DefaultGroupBy(),
                new HavingTrue(),
                null,
                false  /* true means distinct */
        )).execute();
        Table t = (new SelectCommand(
                new SelectStar(Orders),
                new From(new Table[]{Orders}),
                new Where() {

                    @Override
                    public boolean where(Row[] rows) {
                        Integer i = (Integer)rows[0].get("CustomerId");
                        return (i!=null && i.equals(subTable.value()));
                    }
                },
                new DefaultGroupBy(),
                new HavingTrue(),
                null,
                false  /* true means distinct */
        )).execute();

        assertEquals(3,t.size());
        ResultSet rs = t.getResultSet();
        rs.next();
        assertEquals(2, rs.getInt("Id"));
        rs.next();
        assertEquals(3, rs.getInt("Id"));
        rs.next();
        assertEquals(4, rs.getInt("Id"));
    }

    public void testSimpleSubquery_SingleRecord() throws Exception {
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
        Customers.insert(new Object[]{2, "Goto", 3,30});
        Customers.insert(new Object[]{3, "Honda", 3,30});
        Customers.insert(new Object[]{4, "Motohashi", 3,18});
        Customers.insert(new Object[]{5, "Matsumoto", 3,18});

		//Books
		Books.insert(new Object[]{20, 1234567890, "The Art of C++", 20, 1987, 20, 1});
        Books.insert(new Object[]{21, 1234567891, "The Art of C#", 25, 2000, 21, 1});
        Books.insert(new Object[]{22, 1234567892, "The Art of Lisp", 30, 1980, 20, 1});
        Books.insert(new Object[]{23, 1234567894, "Java HandBook", 5, 1999, 21, 1});

		//Publishers
        Publishers.insert(new Object[]{20, "Pearson Education"});
        Publishers.insert(new Object[]{21, "O Reilly"});

		//Orders
//        Orders.insert(new Object[]{20, 1, 20120310, null, 20, 0});
        Orders.insert(new Object[]{1, 1, 20120310, null, 20, 0});
        Orders.insert(new Object[]{2, 2, 20120310, null, 20, 0});
        Orders.insert(new Object[]{3, 2, 20120310, null, 22, 0});
        Orders.insert(new Object[]{4, 2, 20120310, null, 23, 0});
        Orders.insert(new Object[]{5, 4, 20120310, null, 23, 0});

//        ResultSet rs = statement.executeQuery("select * from Orders where customerId = (select id from Customers where name == \"Goto\" )");
        final Table subTable = (new SelectCommand(
                new SimpleSingleTableSelect(new String[]{"Id"}),
                new From(new Table[]{Customers}),
                new Where() {

                    @Override
                    public boolean where(Row[] rows) {
                        Integer age = (Integer)rows[0].get("Age");
                        return (age!=null && age >= 20);
                    }
                },
                new DefaultGroupBy(),
                new HavingTrue(),
                null,
                false  /* true means distinct */
        )).execute();
        Table t = (new SelectCommand(
                new SelectStar(Orders),
                new From(new Table[]{Orders}),
                new Where() {

                    @Override
                    public boolean where(Row[] rows) {
                        Integer i = (Integer)rows[0].get("CustomerId");
                        return (i!=null && subTable.in(i));
                    }
                },
                new DefaultGroupBy(),
                new HavingTrue(),
                null,
                false  /* true means distinct */
        )).execute();

        assertEquals(4,t.size());
        ResultSet rs = t.getResultSet();
        rs.next();
        assertEquals(1, rs.getInt("Id"));
        rs.next();
        assertEquals(2, rs.getInt("Id"));
        rs.next();
        assertEquals(3, rs.getInt("Id"));
        rs.next();
        assertEquals(4, rs.getInt("Id"));
    }

    public void testSimpleSubquery_Any() throws Exception {
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
        Customers.insert(new Object[]{2, "Goto", 3,30});
        Customers.insert(new Object[]{3, "Honda", 3,30});
        Customers.insert(new Object[]{4, "Motohashi", 3,18});
        Customers.insert(new Object[]{5, "Matsumoto", 3,18});

		//Books
		Books.insert(new Object[]{20, 1234567890, "The Art of C++", 20, 1987, 20, 1});
        Books.insert(new Object[]{21, 1234567891, "The Art of C#", 25, 2000, 21, 1});
        Books.insert(new Object[]{22, 1234567892, "The Art of Lisp", 30, 1980, 20, 1});
        Books.insert(new Object[]{23, 1234567894, "Java HandBook", 5, 1999, 21, 1});

		//Publishers
        Publishers.insert(new Object[]{20, "Pearson Education"});
        Publishers.insert(new Object[]{21, "O Reilly"});

		//Orders
//        Orders.insert(new Object[]{20, 1, 20120310, null, 20, 0});
        Orders.insert(new Object[]{1, 1, 20120310, null, 20, 0});
        Orders.insert(new Object[]{2, 2, 20120310, null, 20, 0});
        Orders.insert(new Object[]{3, 2, 20120310, null, 22, 0});
        Orders.insert(new Object[]{4, 2, 20120310, null, 23, 0});
        Orders.insert(new Object[]{5, 4, 20120310, null, 23, 0});

//        ResultSet rs = statement.executeQuery("select * from Orders where customerId = (select id from Customers where name == \"Goto\" )");
        final Table subTable = (new SelectCommand(
                new SimpleSingleTableSelect(new String[]{"Id"}),
                new From(new Table[]{Customers}),
                new Where() {

                    @Override
                    public boolean where(Row[] rows) {
                        Integer age = (Integer)rows[0].get("Age");
                        return (age!=null && age < 22);
                    }
                },
                new DefaultGroupBy(),
                new HavingTrue(),
                null,
                false  /* true means distinct */
        )).execute();
        Table t = (new SelectCommand(
                new SelectStar(Orders),
                new From(new Table[]{Orders}),
                new Where() {

                    @Override
                    public boolean where(Row[] rows) {
                        Integer i = (Integer)rows[0].get("CustomerId");
                        return (i!=null && subTable.any(i, new Predicate() {
                            public boolean predicate(Object o1, Object o2) {
                                return (Integer)o1 > (Integer)o2;
                            }
                        }));
                    }
                },
                new DefaultGroupBy(),
                new HavingTrue(),
                null,
                false  /* true means distinct */
        )).execute();

        assertEquals(4,t.size());
        ResultSet rs = t.getResultSet();
        rs.next();
        assertEquals(2, rs.getInt("Id"));
        rs.next();
        assertEquals(3, rs.getInt("Id"));
        rs.next();
        assertEquals(4, rs.getInt("Id"));
        rs.next();
        assertEquals(5, rs.getInt("Id"));
    }

    public  void testTestme4(){

    	Table Customers = TableFactory.create("Customers", new String[]{"Id",
                "Name", "PasswordHash", "Age"}, new int[]{Table.INT,
                Table.STRING, Table.INT, Table.INT}, new int[]{Table.PRIMARY,
                Table.NONE, Table.NONE, Table.NONE}, new ForeignKey[]{null, null, null,
                null});

    	Customers.insert(new Object[]{0, "Tanaka", 3,25});
        Customers.insert(new Object[]{1, "Suzuki", 3,21});
        Customers.insert(new Object[]{2, "Goto", 3,28});
        Customers.insert(new Object[]{3, "Honda", 3,30});

        Table t = (new SelectCommand(
                new SimpleSingleTableSelect(new String[]{"Id","Age"}),
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

        if(t.size() >= 1){
        	System.out.println("table is not empty");
        }
        System.out.println("Table size = "+t.size());
    }

}
