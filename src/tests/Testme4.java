/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package tests;

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
