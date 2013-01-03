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

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.interpreters;

import java.util.ArrayList;
import java.util.LinkedList;


public class SymbolicStringExpression {
    LinkedList list;

    public SymbolicStringExpression(int sym, IntValue length) {
        this.list = new LinkedList();
        this.list.addLast(new SymbolicStringVar(sym, length));
    }

    public SymbolicStringExpression(SymbolicStringExpression sym) {
        this.list = new LinkedList();
        for (Object elem:sym.list) {
            this.list.addLast(elem);
        }
    }

    public SymbolicStringExpression concatStr(String str) {
        SymbolicStringExpression ret = new SymbolicStringExpression(this);
        Object last = ret.list.getLast();
        if (last instanceof String) {
            ret.list.removeLast();
            ret.list.addLast(last + str);
        } else {
            ret.list.addLast(str);
        }
        return ret;
    }

    public SymbolicStringExpression concat(SymbolicStringExpression expr) {
        SymbolicStringExpression ret = new SymbolicStringExpression(this);
        Object last = ret.list.getLast();
        Object first = expr.list.getFirst();
        if (last instanceof String && first instanceof String) {
            ret.list.removeLast();
            ret.list.addLast(last + first.toString());
        } else {
            ret.list.addLast(first);
        }

        boolean flag = true;
        for(Object elem:expr.list) {
            if (flag) {
                flag = false;
            } else {
                ret.list.addLast(elem);
            }
        }

        return ret;
    }

    public SymbolicStringExpression concatToStr(String str) {
        SymbolicStringExpression ret = new SymbolicStringExpression(this);
        Object first = ret.list.getFirst();
        if (first instanceof String) {
            ret.list.removeFirst();
            ret.list.addFirst(str + first);
        } else {
            ret.list.addFirst(str);
        }
        return ret;
    }

    public boolean isCompound() {
        return this.list.size() > 1;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        boolean flag = true;
        for(Object elem:this.list) {
            if (flag) {
                flag = false;
            } else {
                sb.append('+');
            }
            if (elem instanceof SymbolicStringVar) {
                sb.append(elem.toString());
            } else {
                sb.append('"');
                sb.append(elem);
                sb.append('"');
            }
        }
        return sb.toString();
    }

    public SymbolicStringExpression substitute(ArrayList<Value> assignments) {
        return this;
    }

    public IntValue getField(String offset) {
        if (offset.equals("length")) {
            IntValue ret = null, len;
            for(Object val:this.list) {
                if (val instanceof String) {
                    len = new IntValue(((String)val).length());
                } else {
                    len = (IntValue)((SymbolicStringVar)val).getField("length");
                }
                if (ret == null) {
                    ret = len;
                } else {
                    ret = ret.IADD(len);
                }
            }
            return ret;

        }
        return null;
    }

}
