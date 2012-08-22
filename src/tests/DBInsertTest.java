package tests;

import catg.CATG;
import database.table.internals.ForeignKey;
import database.table.SymbolicTable;
import database.table.internals.Table;
import database.table.internals.TableFactory;

public class DBInsertTest {
	public static int l_age;

	public  static void testme(Table customers, int age){
		l_age = age;

		Object[] newCustomer = {1, "Taro", age, 333};
		customers.insert(newCustomer);

		System.out.println("age="+age);
		System.out.println("reach goal!!.");
    }

    public static void main(String[] args){
    	int age= CATG.readInt(20);

        Table customers = TableFactory.create("Customers", new String[] { "Id",
				"Name", "PasswordHash", "Age" }, new int[] { Table.INT,
				Table.STRING, Table.INT, Table.INT }, new boolean[] { true,
				false, false, false }, new ForeignKey[] { null, null, null,
				null });
        SymbolicTable.insertSymbolicRows(customers, 4);

        testme(customers, age);
    }
}
