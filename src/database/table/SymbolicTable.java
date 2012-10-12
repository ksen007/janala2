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

/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package database.table;

import database.table.internals.ForeignKey;
import database.table.internals.Row;
import database.table.internals.Table;
import database.table.internals.TableIterator;
import janala.Main;
import janala.interpreters.OrValue;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 7/2/12
 * Time: 11:47 PM
 */
public class SymbolicTable {

    public static void insertSymbolicRows(Table table, int n) {
        insertSymbolicRows(table,n,true);
    }

    public static void insertSymbolicRowsNoCheck(Table table, int n) {
        insertSymbolicRows(table,n,false);
    }

    private static void insertSymbolicRows(Table table, int n, boolean checkConsistency) {
        int[] types = table.getColumnTypes();
        boolean[] primaries = table.getPrimaries();
        String[] columnNames = table.getColumnNames();
        ForeignKey[] foreignKeys = table.getForeignKeys();
        for (int i=0; i<n; i++) {
            Object[] row = new Object[types.length];
            System.out.print(table.getName()+" {");
            for (int j=0; j<row.length; j++) {
                if (types[j]==Table.INT) {
                    int x = Main.readInt(0);
                    Main.MakeSymbolic(x);
                    Integer k = new Integer(x);
                    row[j] = k;
                } else if (types[j]==Table.LONG) {
                    long x = Main.readLong(0);
                    Main.MakeSymbolic(x);
                    Long k = new Long(x);
                    row[j] = k;
                } else if (types[j]==Table.DATE) {
                    long x = Main.readLong(1);
                    Main.MakeSymbolic(x);
                    Main.Assume(x>0?1:0);
                    java.sql.Date k = new java.sql.Date(x);
                    row[j] = k;
                } else if (types[j]==Table.TIME) {
                    long x = Main.readLong(1);
                    Main.MakeSymbolic(x);
                    Main.Assume(x>0?1:0);
                    java.sql.Time k = new java.sql.Time(x);
                    row[j] = k;
                } else if (types[j]==Table.TIMESTAMP) {
                    long x = Main.readLong(1);
                    Main.MakeSymbolic(x);
                    Main.Assume(x>0?1:0);
                    java.sql.Timestamp k = new java.sql.Timestamp(x);
                    row[j] = k;
                } else if (types[j]==Table.STRING) {
                    String x = Main.readString("");
                    Main.MakeSymbolic(x);
                    row[j] = x;
                }
                System.out.print(row[j]);
                System.out.print(" , ");

                if (checkConsistency) {
                    // now assume primary key constraint
                    if (primaries[j]) {
                        TableIterator iter = table.iterator();
                        while (iter.hasNext()) {
                            Row otherRow = iter.next();
                            Main.Assume(row[j].equals(otherRow.get(columnNames[j]))?0:1);
                        }
                    }

                    // now assume foreign key constraints
                    if (foreignKeys[j]!=null) {
                        OrValue tmp = null;
                        TableIterator iter = foreignKeys[j].table.iterator();
                        while (iter.hasNext()) {
                            Row otherRow = iter.next();
                            if (tmp==null) {
                                Main.Ignore();
                                tmp = Main.AssumeOrBegin(row[j].equals(otherRow.get(foreignKeys[j].key))?1:0);
                            } else {
                                Main.Ignore();
                                tmp = Main.AssumeOr(row[j].equals(otherRow.get(foreignKeys[j].key))?1:0,tmp);
                            }
                        }
                        if (tmp!=null) {
                            Main.AssumeOrEnd(tmp);
                        }
                    }
                }
            }
            System.out.println("}");

            if (checkConsistency)
                table.insert(row);
            else
                table.insertNoCheck(row);
        }
    }
}
