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

			public boolean where(Row[] rows) {
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
                Table.STRING, Table.INT, Table.INT}, new int[]{Table.PRIMARY,
                Table.NONE, Table.NONE, Table.NONE}, new ForeignKey[]{null, null, null,
                null});
        SymbolicTable.insertSymbolicRows(customers, 4);

        testme(customers, age);
    }
}
