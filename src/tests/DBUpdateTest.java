package tests;

import catg.CATG;
import database.table.*;
import database.table.internals.ForeignKey;
import database.table.internals.Row;
import database.table.internals.Table;
import database.table.internals.TableFactory;
import database.table.where.Where;

public class DBUpdateTest {
	public static int l_age;

	public  static void testme(Table customers, int age){
		l_age = age;

		int updatedCount = customers.update(new Where() {
			public boolean modify(Row rows) {
				Integer age = (Integer) rows.get("Age");
				if (age != null && age == 30) {
					Integer oldAge = (Integer) rows.get("Age");
					rows.put("Age", oldAge+20);
					return true;
				} else {
					return false;
				}
			}

			public boolean isTrue(Row[] rows) {
				return true;
			}
		});

		System.out.println("age="+age);
		System.out.println(updatedCount + " records are updated.");
		if(updatedCount >3){
			System.out.println("reach goal!!.");
		}
		else{
			System.out.println("not reach goal...");
		}
    }

    public static void main(String[] args){
    	int age= CATG.readInt(20);

        Table customers = TableFactory.create("Customers", new String[]{"Id",
                "Name", "PasswordHash", "Age"}, new int[]{Table.INT,
                Table.STRING, Table.INT, Table.INT}, new boolean[]{true,
                false, false, false}, new ForeignKey[]{null, null, null,
                null});
        SymbolicTable.insertSymbolicRows(customers, 4);

        testme(customers, age);
    }
}
