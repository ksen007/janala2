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

import database.table.orderby.OrderBy;
import database.table.where.Where;

import java.sql.ResultSet;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 7/2/12
 * Time: 12:32 PM
 */
public interface Table {
    final public static int INT = 1;
    final public static int STRING = 2;
    final public static int LONG = 3;
    final public static int DATE = 4;
    final public static int TIME = 5;
    final public static int TIMESTAMP = 6;
    int NONE = 0;
    int PRIMARY = 1;
    int UNIQUE = 2;
    int NONNULL = 4;

    public void insert(Row row);

    public void insert(String[] columns, Object[] values);

    public void insert(Object[] values);

    public TableIterator iterator();

    public int delete(Where where);

    public int update(Where where);

    public Table select(Where where, String[][] selectColumns, Table[] fromOther);

    public ResultSet getResultSet();

    public String getName();

    public String[] getColumnNames();

    public int[] getColumnTypes();

    public boolean[] getPrimaries();

    public boolean[] getUniques();

    public boolean[] getNonNulls();

    public ForeignKey[] getForeignKeys();

    public void orderBy(OrderBy orderBy);

    public int size();

    public Object value();

    public boolean in(Object o);

    public boolean any(Object o, Predicate p);
}
