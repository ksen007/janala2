package tests.db;

import database.table.SymbolicTable;
import database.table.commands.SelectCommand;
import database.table.from.From;
import database.table.groupby.DefaultGroupBy;
import database.table.having.HavingTrue;
import database.table.internals.ForeignKey;
import database.table.internals.Row;
import database.table.internals.Table;
import database.table.internals.TableFactory;
import database.table.select.SimpleMultiTableSelect;
import database.table.where.Where;

public class Cyclic {
	public static void main(String[] args){

		Table A;
		Table B;

    	A = TableFactory.create("A",
    			new String[] { "C1", "C2" },
    			new int[] { Table.INT, Table.INT },
				new int[] { Table.NONE, Table.NONE },
				new ForeignKey[] { null, null});

		B = TableFactory.create("B",
				new String[] { "C1", "C2" },
				new int[] { Table.INT, Table.INT},
				new int[] { Table.NONE, Table.NONE },
				new ForeignKey[] { null, new ForeignKey(A, "C1")});

        A.setForeignKeys(new ForeignKey[] { null, new ForeignKey(B, "C1") });
		SymbolicTable.insertSymbolicRows(A, 1);
		SymbolicTable.insertSymbolicRows(B, 2);


    }
}