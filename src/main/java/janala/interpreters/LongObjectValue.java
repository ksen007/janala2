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

import janala.solvers.History;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 7/8/12
 * Time: 9:10 AM
 */
public class LongObjectValue extends ObjectValue {
    LongValue longValue;

    public LongObjectValue() {
        super(100,-1);
    }

    @Override
    public Value invokeMethod(String name, Value[] args, History history) {
        if (name.equals("<init>")) {
            if (args[0] instanceof LongValue)
                this.longValue = (LongValue)args[0];
            else
                this.longValue = new LongValue(Long.parseLong(((StringValue) args[0]).getConcrete()));
            return PlaceHolder.instance;
        } else if(name.equals("intValue")) {
            return new IntValue((int) longValue.concrete, longValue.symbolic);
        } else if(name.equals("longValue")) {
            return new LongValue((long) longValue.concrete, longValue.symbolic);
        } else if(name.equals("shortValue")) {
            return new IntValue((short) longValue.concrete, longValue.symbolic);
        } else if(name.equals("byteValue")) {
            return new IntValue((byte) longValue.concrete, longValue.symbolic);
        } else if(name.equals("equals")) {
            if (args[0] instanceof LongObjectValue) {
                LongObjectValue i2 = (LongObjectValue)args[0];
                LongValue ret = longValue.LSUB(i2.longValue);
                ret.concrete = ret.concrete==0?1:0;
                if (ret.symbolic!=null) {
                    ret.symbolic = ret.symbolic.setop(SymbolicInt.COMPARISON_OPS.EQ);
                    IntValue ret2 = new IntValue((int)ret.concrete,ret.symbolic);
                    return ret2;
                }
            }
        } else if(name.equals("compareTo")) {
            if (args[0] instanceof LongObjectValue) {
                LongObjectValue i2 = (LongObjectValue)args[0];
                LongValue ret = longValue.LSUB(i2.longValue);
                if (ret.concrete>0) ret.concrete=1;
                else if (ret.concrete==0) ret.concrete = 0;
                else ret.concrete = -1;
                IntValue ret2 = new IntValue((int)ret.concrete,ret.symbolic);
                return ret2;
            }
        }
        return PlaceHolder.instance;
    }
}
