/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package tests;

import database.table.*;
import database.table.internals.ForeignKey;
import database.table.internals.Row;
import database.table.internals.Table;
import database.table.internals.TableFactory;
import database.table.where.Where;
import janala.Main;

import java.sql.ResultSet;

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
                    new boolean[]{true, false},
                    new ForeignKey[]{null, null});

            SymbolicTable.insertSymbolicRows(Customers, 2);


            cid = Main.readString("hello");
            Main.MakeSymbolic(cid);

            ResultSet rs = Customers.select(new Where() {
                public boolean isTrue(Row[] rows) {
                    if (!cid.equals(rows[0].get("Id"))) return false;
                    return true;
                }
            },new String[][]{{"Id"}},null).getResultSet();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
