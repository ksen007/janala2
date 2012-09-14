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

import java.sql.ResultSet;

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

    /* select Id, Age from Customers where PasswordHash > 10 */

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
        assertEquals(4, t.size());
        ResultSet rs = t.getResultSet();
        rs.next();
        assertEquals(1,rs.getInt("Id"));
        assertEquals(23,rs.getInt("Age"));
        rs.next();
        assertEquals(9,rs.getInt("Id"));
        assertEquals(23,rs.getInt("Age"));
        rs.next();
        assertEquals(7,rs.getInt("Id"));
        assertEquals(24,rs.getInt("Age"));
        rs.next();
        assertEquals(4,rs.getInt("Id"));
        assertEquals(24,rs.getInt("Age"));
    }

    /* select distinct Name, Age from Customers where PasswordHash > 10 */
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
        assertEquals(3, t.size());
        ResultSet rs = t.getResultSet();
        rs.next();
        assertEquals("A",rs.getString("Name"));
        assertEquals(23,rs.getInt("Age"));
        rs.next();
        assertEquals("C",rs.getString("Name"));
        assertEquals(24,rs.getInt("Age"));
        rs.next();
        assertEquals("D",rs.getString("Name"));
        assertEquals(24,rs.getInt("Age"));
    }

        /* select Id, Age from Customers  */
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
        ResultSet rs = t.getResultSet();
        rs.next();
        assertEquals(1,rs.getInt("Id"));
        assertEquals(23,rs.getInt("Age"));
        rs.next();
        assertEquals(9,rs.getInt("Id"));
        assertEquals(23,rs.getInt("Age"));
        rs.next();
        assertEquals(7,rs.getInt("Id"));
        assertEquals(24,rs.getInt("Age"));
        rs.next();
        assertEquals(4,rs.getInt("Id"));
        assertEquals(24,rs.getInt("Age"));
        rs.next();
        assertEquals(3,rs.getInt("Id"));
        assertEquals(23,rs.getInt("Age"));
    }

        /* select distinct Name, Age from Customers */

    public void testSimpleSingleTableSelectDistinct() throws Exception {
        Table t = (new SelectCommand(
                new SimpleSingleTableSelect(new String[]{"Name","Age"}),
                new From(new Table[]{customers}),
                new WhereTrue(),
                new DefaultGroupBy(),
                new HavingTrue(),
                null, true
        )).execute();
        ResultSet rs = t.getResultSet();
        rs.next();
        assertEquals("A",rs.getString("Name"));
        assertEquals(23,rs.getInt("Age"));
        rs.next();
        assertEquals("C",rs.getString("Name"));
        assertEquals(24,rs.getInt("Age"));
        rs.next();
        assertEquals("D",rs.getString("Name"));
        assertEquals(24,rs.getInt("Age"));
        rs.next();
        assertEquals("E",rs.getString("Name"));
        assertEquals(23,rs.getInt("Age"));
        assertEquals(4, t.size());
    }

        /* select Id, Age from Customers  order by Id, Age */
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
        ResultSet rs = t.getResultSet();
        rs.next();
        assertEquals(1,rs.getInt("Id"));
        assertEquals(23,rs.getInt("Age"));
        rs.next();
        assertEquals(3,rs.getInt("Id"));
        assertEquals(23,rs.getInt("Age"));
        rs.next();
        assertEquals(9,rs.getInt("Id"));
        assertEquals(23,rs.getInt("Age"));
        rs.next();
        assertEquals(4,rs.getInt("Id"));
        assertEquals(24,rs.getInt("Age"));
        rs.next();
        assertEquals(7,rs.getInt("Id"));
        assertEquals(24,rs.getInt("Age"));
    }

    /* select Id, Age from Customers  group by Age */
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
        ResultSet rs = t.getResultSet();
        rs.next();
        assertEquals(1,rs.getInt("Id"));
        assertEquals(23,rs.getInt("Age"));
        rs.next();
        assertEquals(7,rs.getInt("Id"));
        assertEquals(24,rs.getInt("Age"));
    }

    /* select Age, MAX(Id) from Customers  group by Age */
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
        ResultSet rs = t.getResultSet();
        rs.next();
        assertEquals(9,rs.getInt("MAX(Id)"));
        assertEquals(23,rs.getInt("Age"));
        rs.next();
        assertEquals(7,rs.getInt("MAX(Id)"));
        assertEquals(24,rs.getInt("Age"));
    }

    /* select Age, MIN(Id) from Customers  group by Age */
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
        ResultSet rs = t.getResultSet();
        rs.next();
        assertEquals(1,rs.getInt("MIN(Id)"));
        assertEquals(23,rs.getInt("Age"));
        rs.next();
        assertEquals(4,rs.getInt("MIN(Id)"));
        assertEquals(24,rs.getInt("Age"));
    }

    /* select Age, SUM(Id) from Customers  group by Age */
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
        ResultSet rs = t.getResultSet();
        rs.next();
        assertEquals(13,rs.getInt("SUM(Id)"));
        assertEquals(23,rs.getInt("Age"));
        rs.next();
        assertEquals(11,rs.getInt("SUM(Id)"));
        assertEquals(24,rs.getInt("Age"));
    }

    /* select Age, COUNT(Id) from Customers  group by Age */
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
        ResultSet rs = t.getResultSet();
        rs.next();
        assertEquals(3,rs.getInt("COUNT(Id)"));
        assertEquals(23,rs.getInt("Age"));
        rs.next();
        assertEquals(2,rs.getInt("COUNT(Id)"));
        assertEquals(24,rs.getInt("Age"));
    }
}
