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

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 7/2/12
 * Time: 12:33 PM
 */
public class TableFactory {
    private static int anonymousTableCount = 0;


    public static Table create(String[] columnNames) {
        anonymousTableCount++;
        return new TableImpl("InternalTable"+anonymousTableCount,columnNames);
    }

    public static Table create(String name, String[] columnNames, int[] columnTypes, int[] attributes, ForeignKey[] foreignKeys) {
        assert(columnNames.length==columnTypes.length && columnNames.length == attributes.length && columnNames.length == foreignKeys.length);
        boolean[] primaries = new boolean[attributes.length];
        boolean[] uniques = new boolean[attributes.length];
        boolean[] nonulls = new boolean[attributes.length];
        for (int i = 0; i < attributes.length; i++) {
            int attribute = attributes[i];
            primaries[i] = (attribute & Table.PRIMARY) != 0;
            uniques[i] = (attribute & Table.UNIQUE) != 0;
            nonulls[i] = (attribute & Table.NONNULL) != 0;
        }
        return new TableImpl(name,columnNames,columnTypes,primaries,uniques,nonulls,foreignKeys);
    }
}
