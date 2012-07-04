package tests;

import database.table.SymbolicTable;
import database.table.Table;
import database.table.TableFactory;
import database.table.Where;
import janala.Main;

import java.sql.ResultSet;
import java.util.Map;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 7/3/12
 * Time: 5:02 PM
 */
public class SimpleDBTest {


    private static Table Customers;
    private static int cid;
    private static int pwd;

    public static void main(String[] argv){
        try {
            Customers = TableFactory.create("Customers",
                    new String[]{"Id", "PasswordHash"},
                    new int[]{Table.INT, Table.INT},
                    new boolean[]{true, false});

            SymbolicTable.insertSymbolicRows(Customers, 1);


            cid = Main.readInt(0);
            Main.MakeSymbolic(cid);
            pwd = Main.readInt(1);
            Main.MakeSymbolic(pwd);


            ResultSet rs = Customers.select(new Where() {
                public boolean isTrue(Map<String, Object>[] rows) {
                    Integer i = (Integer)rows[0].get("Id");
                    if (i==null || i!=cid) return false;
                    return true;
                }
            },new String[][]{{"Id"}},null).getResultSet();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int hash(int password) {
        return password % 5 + 3;
    }


}
