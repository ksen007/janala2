/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

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
 * Date: 7/4/12
 * Time: 11:25 PM
 */
public class DBStringTest {
    private static Table Customers;
    private static String cid;

    public static void main(String[] argv){
        try {
            Customers = TableFactory.create("Customers",
                    new String[]{"Id", "PasswordHash"},
                    new int[]{Table.STRING, Table.STRING},
                    new boolean[]{true, false});

            SymbolicTable.insertSymbolicRows(Customers, 2);


            cid = Main.readString("hello");
            Main.MakeSymbolic(cid);

            ResultSet rs = Customers.select(new Where() {
                public boolean isTrue(Map<String, Object>[] rows) {
                    if (!cid.equals(rows[0].get("Id"))) return false;
                    return true;
                }
            },new String[][]{{"Id"}},null).getResultSet();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
