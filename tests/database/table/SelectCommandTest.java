/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package database.table;

import database.table.commands.SelectCommand;
import database.table.from.From;
import database.table.groupby.DefaultGroupBy;
import database.table.groupby.GroupBy;
import database.table.groupby.SimpleGroupBy;
import database.table.having.HavingTrue;
import database.table.internals.*;
import database.table.operations.*;
import database.table.orderby.SimpleOrderBy;
import database.table.select.OperationSingleTableSelect;
import database.table.select.Select;
import database.table.select.SimpleSingleTableSelect;
import database.table.where.Where;
import database.table.where.WhereTrue;
import junit.framework.TestCase;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 8/21/12
 * Time: 10:37 PM
 */
public class SelectCommandTest extends TestCase {
    private Table customers;

    public void setUp() throws Exception {
        customers = TableFactory.create("Customers", new String[]{"Id",
                "Name", "PasswordHash", "Age"}, new int[]{Table.INT,
                Table.STRING, Table.INT, Table.INT}, new int[]{Table.PRIMARY,
                Table.NONE, Table.NONE, Table.NONE}, new ForeignKey[]{null, null, null,
                null});
        customers.insert(new Object[]{1,"A",12,23});
        customers.insert(new Object[]{9,"A",13,23});
        customers.insert(new Object[]{7,"C",19,24});
        customers.insert(new Object[]{4,"D",11,24});
        customers.insert(new Object[]{3,"E",10,23});

    }

    public void tearDown() throws Exception {

    }

    public void testExecute() throws Exception {

    }

    public void testSelect1() throws Exception {
        Table t = (new SelectCommand(
                new Select() {
                    public String[] selectAs() {
                        return new String[]{"Id","Age"};
                    }

                    public Operations[] select() {
                        return new Operations[]{new IdentityOperation("Id"), new IdentityOperation("Age")};
                    }
                },
                new From(new Table[]{customers}),
                new Where() {
                    @Override
                    public boolean where(Row[] rows) {
                        Integer i = (Integer) rows[0].get("PasswordHash");
                        return (i!=null && i > 10);
                    }

                },
                new GroupBy() {
                    int i = 0;
                    public Object[] groupBy(Row[] rows) {
                        i++;
                        return new Object[] {i};
                    }
                },
                new HavingTrue(),
                null, false
        )).execute();
        assertEquals(4,t.size());
        TableIterator iter = t.iterator();
        System.out.println(iter.next());
        System.out.println(iter.next());
        System.out.println(iter.next());
        System.out.println(iter.next());
    }

    public void testSelectDistinct() throws Exception {
        Table t = (new SelectCommand(
                new Select() {
                    public String[] selectAs() {
                        return new String[]{"Name","Age"};
                    }

                    public Operations[] select() {
                        return new Operations[]{new IdentityOperation("Name"), new IdentityOperation("Age")};
                    }
                },
                new From(new Table[]{customers}),
                new Where() {
                    @Override
                    public boolean where(Row[] rows) {
                        Integer i = (Integer) rows[0].get("PasswordHash");
                        return (i!=null && i > 10);
                    }

                },
                new GroupBy() {
                    int i = 0;
                    public Object[] groupBy(Row[] rows) {
                        i++;
                        return new Object[] {i};
                    }
                },
                new HavingTrue(),
                null, true
        )).execute();
        TableIterator iter = t.iterator();
        System.out.println(iter.next());
        System.out.println(iter.next());
        System.out.println(iter.next());
        assertEquals(3, t.size());
    }

    public void testSimpleSingleTableSelect() throws Exception {
        Table t = (new SelectCommand(
                new SimpleSingleTableSelect(new String[]{"Id","Age"}),
                new From(new Table[]{customers}),
                new WhereTrue(),
                new DefaultGroupBy(),
                new HavingTrue(),
                null, false
        )).execute();
        assertEquals(5,t.size());
        TableIterator iter = t.iterator();
        System.out.println(iter.next());
        System.out.println(iter.next());
        System.out.println(iter.next());
        System.out.println(iter.next());
        System.out.println(iter.next());
    }

    public void testSimpleSingleTableSelectDistinct() throws Exception {
        Table t = (new SelectCommand(
                new SimpleSingleTableSelect(new String[]{"Name","Age"}),
                new From(new Table[]{customers}),
                new WhereTrue(),
                new DefaultGroupBy(),
                new HavingTrue(),
                null, true
        )).execute();
        TableIterator iter = t.iterator();
        System.out.println(iter.next());
        System.out.println(iter.next());
        System.out.println(iter.next());
        System.out.println(iter.next());
        assertEquals(4, t.size());
    }

    public void testSimpleOrderBySelect() throws Exception {
        Table t = (new SelectCommand(
                new SimpleSingleTableSelect(new String[]{"Id","Age"}),
                new From(new Table[]{customers}),
                new WhereTrue(),
                new DefaultGroupBy(),
                new HavingTrue(),
                new SimpleOrderBy(new String[]{"Age","Id"},true), false
        )).execute();
        assertEquals(5,t.size());
        TableIterator iter = t.iterator();
        System.out.println(iter.next());
        System.out.println(iter.next());
        System.out.println(iter.next());
        System.out.println(iter.next());
        System.out.println(iter.next());
    }

    public void testSimpleGroupBySelect() throws Exception {
        Table t = (new SelectCommand(
                new SimpleSingleTableSelect(new String[]{"Id","Age"}),
                new From(new Table[]{customers}),
                new WhereTrue(),
                new SimpleGroupBy(new String[]{"Age"}),
                new HavingTrue(),
                null, false
        )).execute();
        assertEquals(2,t.size());
        TableIterator iter = t.iterator();
        System.out.println(iter.next());
        System.out.println(iter.next());
    }

    public void testMaxGroupBySelect() throws Exception {
        Table t = (new SelectCommand(
                new OperationSingleTableSelect(new StandardOperation[]{new IdentityOperation("Age"), new MaxOperation("Id")}),
                new From(new Table[]{customers}),
                new WhereTrue(),
                new SimpleGroupBy(new String[]{"Age"}),
                new HavingTrue(),
                null, false
        )).execute();
        assertEquals(2,t.size());
        TableIterator iter = t.iterator();
        System.out.println(iter.next());
        System.out.println(iter.next());
    }

    public void testMinGroupBySelect() throws Exception {
        Table t = (new SelectCommand(
                new OperationSingleTableSelect(new StandardOperation[]{new IdentityOperation("Age"), new MinOperation("Id")}),
                new From(new Table[]{customers}),
                new WhereTrue(),
                new SimpleGroupBy(new String[]{"Age"}),
                new HavingTrue(),
                null, false
        )).execute();
        assertEquals(2,t.size());
        TableIterator iter = t.iterator();
        System.out.println(iter.next());
        System.out.println(iter.next());
    }

    public void testSumGroupBySelect() throws Exception {
        Table t = (new SelectCommand(
                new OperationSingleTableSelect(new StandardOperation[]{new IdentityOperation("Age"), new SumOperation("Id")}),
                new From(new Table[]{customers}),
                new WhereTrue(),
                new SimpleGroupBy(new String[]{"Age"}),
                new HavingTrue(),
                null, false
        )).execute();
        assertEquals(2,t.size());
        TableIterator iter = t.iterator();
        System.out.println(iter.next());
        System.out.println(iter.next());
    }

    public void testCountGroupBySelect() throws Exception {
        Table t = (new SelectCommand(
                new OperationSingleTableSelect(new StandardOperation[]{new IdentityOperation("Age"), new CountOperation("Id")}),
                new From(new Table[]{customers}),
                new WhereTrue(),
                new SimpleGroupBy(new String[]{"Age"}),
                new HavingTrue(),
                null, false
        )).execute();
        assertEquals(2,t.size());
        TableIterator iter = t.iterator();
        System.out.println(iter.next());
        System.out.println(iter.next());
    }
}
