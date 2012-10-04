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

package tests;

import database.table.SymbolicTable;
import database.table.commands.SelectCommand;
import database.table.from.From;
import database.table.groupby.DefaultGroupBy;
import database.table.having.HavingTrue;
import database.table.internals.ForeignKey;
import database.table.internals.Row;
import database.table.internals.Table;
import database.table.internals.TableFactory;
import database.table.orderby.SimpleOrderBy;
import database.table.select.SimpleSingleTableSelect;
import database.table.where.Where;

public class Testme4 {



    public static void main(String[] args){

    	Table Customers = TableFactory.create("Customers", new String[]{"Id",
                "Name", "PasswordHash", "Age"}, new int[]{Table.INT,
                Table.STRING, Table.INT, Table.INT}, new int[]{Table.PRIMARY,
                Table.NONE, Table.NONE, Table.NONE}, new ForeignKey[]{null, null, null,
                null});

//    	Customers.insert(new Object[]{0, "Tanaka", 3,25});
//        Customers.insert(new Object[]{1, "Suzuki", 3,21});
//        Customers.insert(new Object[]{2, "Goto", 3,28});
//        Customers.insert(new Object[]{3, "Honda", 3,30});
        SymbolicTable.insertSymbolicRows(Customers, 4);

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
