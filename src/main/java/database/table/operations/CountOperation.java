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

package database.table.operations;

import database.table.internals.Row;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 8/22/12
 * Time: 2:00 AM
 */
public class CountOperation extends StandardOperation {
    final private static Object dummy = new Object();

    public CountOperation(int tableIndex, String columnName) {
        super(tableIndex, columnName);
    }

    public CountOperation(String columnName) {
        super(columnName);
    }

    public CountOperation() {
        super(null);
    }

    public Object apply(Object old, Row[] rows) {
        Object nu;
        if (columnName==null)
            nu = dummy;
        else
            nu = rows[tableIndex].get(columnName);
        if (old==null && nu == null) return 0;
        if (old==null) return 1;
        if (nu==null) return old;
        return (Integer)old+1;
    }

    @Override
    protected Object operation(Object aggregate, Object current) {
        throw new RuntimeException("Should not be called.");
    }

    @Override
    public String name() {
        if (columnName==null)
            return "COUNT(*)";
        else
            return "COUNT("+columnName+")";
    }

}
