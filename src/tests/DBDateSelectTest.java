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

package tests;

import database.table.SymbolicTable;
import database.table.internals.ForeignKey;
import database.table.internals.Row;
import database.table.internals.Table;
import database.table.internals.TableFactory;
import database.table.where.Where;

import java.sql.Date;
import java.sql.ResultSet;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 9/3/12
 * Time: 9:39 PM
 */
public class DBDateSelectTest {

	public  static void testme(Table customers){

		ResultSet rs = customers.select(new Where() {
			public boolean where(Row[] rows) {
				Date i = (Date) rows[0].get("DOB");
				if (i != null && i.before(Date.valueOf("1980-11-24")))
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

		System.out.println(selectedCount + " records are selected.");
		if(selectedCount >3){
			System.out.println("reach goal!!.");
		}
		else{
			System.out.println("not reach goal...");
		}
    }

    public static void main(String[] args){
        Table customers = TableFactory.create("Customers", new String[]{"Id",
                "Name", "DOB", "Age"}, new int[]{Table.INT,
                Table.STRING, Table.DATE, Table.INT}, new int[]{Table.PRIMARY,
                Table.NONE, Table.NONE, Table.NONE}, new ForeignKey[]{null, null, null,
                null});
        SymbolicTable.insertSymbolicRows(customers, 4);

        testme(customers);
    }
}

