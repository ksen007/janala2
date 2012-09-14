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

package database.table.internals;

public class ConsistencyChecker {

    public static void checkRow(Table table, Row row) {
        boolean[] primaries = table.getPrimaries();
        boolean[] uniques = table.getUniques();
        boolean[] nonnulls = table.getNonNulls();
        String[] columnNames = table.getColumnNames();
        ForeignKey[] foreignKeys = table.getForeignKeys();

        int cnt = 0;
        for (int j=0; j<columnNames.length; j++) {
            if (primaries != null && primaries[j])
                cnt++;
        }
        if (cnt > 0) {
            Object[] thisPrimary = new Object[cnt];
            int i = 0;
            for (int j=0; j<columnNames.length; j++) {
                if (primaries!=null && primaries[j]) {
                    thisPrimary[i] = row.get(columnNames[j]);
                    assert thisPrimary[i] != null;
                    i++;
                }
            }
            ArrayBasedTuple thisArray = new ArrayBasedTuple(thisPrimary);
            TableIterator iter = table.iterator();
            while (iter.hasNext()) {
                Row otherRow = iter.next();
                if (row!=otherRow) {
                    Object[] otherPrimary = new Object[cnt];
                    i = 0;
                    for (int j=0; j<columnNames.length; j++) {
                        if (primaries!=null && primaries[j]) {
                            otherPrimary[i] = otherRow.get(columnNames[j]);
                            assert otherPrimary[i] != null;
                            i++;
                        }
                    }
                    ArrayBasedTuple otherArray = new ArrayBasedTuple(otherPrimary);
                    assert (!thisArray.equals(otherArray));
                }
            }
        }
        for (int j=0; j<columnNames.length; j++) {
            if (uniques!=null && uniques[j]) {
                Object value = row.get(columnNames[j]);
                assert value != null;
                TableIterator iter = table.iterator();
                while (iter.hasNext()) {
                    Row otherRow = iter.next();
                    if (row!=otherRow)
                        assert (!value.equals(otherRow.get(columnNames[j])));
                }
            }
            if (nonnulls!=null && nonnulls[j]) {
                Object value = row.get(columnNames[j]);
                assert value != null;
            }
        }
        for (int j=0; j<columnNames.length; j++) {
            if (foreignKeys!=null && foreignKeys[j]!=null) {
                Object value = row.get(columnNames[j]);
                assert value != null;
                TableIterator iter = foreignKeys[j].table.iterator();
                boolean found = false;
                while (iter.hasNext()) {
                    Row otherRow = iter.next();
                    found = value.equals(otherRow.get(foreignKeys[j].key));
                    if (found) break;
                }
                assert found;
            }

        }

    }


}
