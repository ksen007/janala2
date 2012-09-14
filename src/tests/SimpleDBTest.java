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
