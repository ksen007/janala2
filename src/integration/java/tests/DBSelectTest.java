package tests;

import catg.CATG;
import database.table.*;
import database.table.internals.ForeignKey;
import database.table.internals.Row;
import database.table.internals.Table;
import database.table.internals.TableFactory;
import database.table.where.Where;

import java.sql.ResultSet;

public class DBSelectTest {
	public static int l_age;

	public  static void testme(Table customers, int age){
		l_age = age;

		ResultSet rs = customers.select(new Where() {
			public boolean where(Row[] rows) {
				Integer i = (Integer) rows[0].get("Age");
				if (i != null && i == l_age)
					return true;
				return false;
			}
		}, new String[][] { { "Id" } }, null).getResultSet();

		int selectedCount = 0;

		try{
			while (rs.next()) {
				selectedCount++;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}

		System.out.println("age="+age);
		System.out.println(selectedCount + " records are selected.");
		if(selectedCount >3){
			System.out.println("reached goal!!.");
		}
		else{
			System.out.println("failed to reach goal...");
		}
    }

    public static void main(String[] args){
    	int age= CATG.readInt(20);

        Table customers = TableFactory.create("Customers", new String[]{"Id",
                "Name", "PasswordHash", "Age"}, new int[]{Table.INT,
                Table.STRING, Table.INT, Table.INT}, new int[]{Table.PRIMARY,
                Table.NONE, Table.NONE, Table.NONE}, new ForeignKey[]{null, null, null,
                null});
        SymbolicTable.insertSymbolicRows(customers, 4);

        testme(customers, age);
    }
}
