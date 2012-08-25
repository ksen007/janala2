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
                    new int[]{Table.PRIMARY, Table.NONE},
                    new ForeignKey[]{null, null});

            SymbolicTable.insertSymbolicRows(Customers, 1);


            cid = Main.readInt(0);
            Main.MakeSymbolic(cid);

            ResultSet rs = Customers.select(new Where() {
                public boolean where(Row[] rows) {
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
