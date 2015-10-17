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

package tests;

import database.table.commands.SelectCommand;
import database.table.from.From;
import database.table.groupby.GroupBy;
import database.table.having.HavingTrue;
import database.table.internals.ForeignKey;
import database.table.internals.Row;
import database.table.internals.Table;
import database.table.internals.TableFactory;
import database.table.operations.IdentityOperation;
import database.table.operations.Operations;
import database.table.select.Select;
import database.table.where.Where;

public class SelectCommandTest {

	private static Table customers;

	public static void main(String[] argv){
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

        int[] types = t.getColumnTypes();
        String[] names = t.getColumnNames();

        System.out.println(types);
        System.out.println(names);

	}



}
