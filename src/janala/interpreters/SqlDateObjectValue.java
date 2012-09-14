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

package janala.interpreters;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */
public class SqlDateObjectValue extends ObjectValue {
    LongValue longValue;

    public SqlDateObjectValue() {
        super(100,-1);
    }

    @Override
    public Value invokeMethod(String name, Value[] args) {
        if (name.equals("<init>")) {
            if (args[0] instanceof LongValue)
                this.longValue = (LongValue)args[0];
            return PlaceHolder.instance;
        } else if(name.equals("equals")) {
            if (args[0] instanceof SqlDateObjectValue) {
                SqlDateObjectValue i2 = (SqlDateObjectValue)args[0];
                LongValue ret = longValue.LSUB(i2.longValue);
                ret.concrete = ret.concrete==0?1:0;
                if (ret.symbolic!=null) {
                    ret.symbolic = ret.symbolic.setop(SymbolicInt.COMPARISON_OPS.EQ);
                    IntValue ret2 = new IntValue((int)ret.concrete,ret.symbolic);
                    return ret2;
                }
            }
        } else if(name.equals("compareTo")) {
            if (args[0] instanceof SqlDateObjectValue) {
                SqlDateObjectValue i2 = (SqlDateObjectValue)args[0];
                LongValue ret = longValue.LSUB(i2.longValue);
                if (ret.concrete>0) ret.concrete=1;
                else if (ret.concrete==0) ret.concrete = 0;
                else ret.concrete = -1;
                IntValue ret2 = new IntValue((int)ret.concrete,ret.symbolic);
                return ret2;
            }
        } else if(name.equals("after")) {
            if (args[0] instanceof SqlDateObjectValue) {
                SqlDateObjectValue i2 = (SqlDateObjectValue)args[0];
                LongValue ret = longValue.LSUB(i2.longValue);
                if (ret.concrete>0) ret.concrete=1;
                else ret.concrete = 0;
                IntValue ret2 = new IntValue((int)ret.concrete,
                                ret.symbolic.setop(SymbolicInt.COMPARISON_OPS.GT));
                return ret2;
            }
        } else if(name.equals("before")) {
            if (args[0] instanceof SqlDateObjectValue) {
                SqlDateObjectValue i2 = (SqlDateObjectValue)args[0];
                LongValue ret = longValue.LSUB(i2.longValue);
                if (ret.concrete<0) ret.concrete=1;
                else ret.concrete = 0;
                IntValue ret2 = new IntValue((int)ret.concrete,
                                ret.symbolic.setop(SymbolicInt.COMPARISON_OPS.LT));
                return ret2;
            }
        } else if(name.equals("getTime")) {
            return new LongValue((long) longValue.concrete, longValue.symbolic);
        } else if (name.equals("setTime")) {
            if (args[0] instanceof LongValue)
                this.longValue = (LongValue)args[0];
            return PlaceHolder.instance;
        }
        return PlaceHolder.instance;
    }
}
