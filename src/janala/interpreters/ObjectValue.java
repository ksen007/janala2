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
 * Date: 6/19/12
 * Time: 8:58 AM
 */
public class ObjectValue extends Value {
    final public static ObjectValue NULL = new ObjectValue(0,0);
    Value[] concrete;
    int address; // address 0 is null, address -1 is uninitialized address

    @Override
    public Object getConcrete() {
        return address;
    }

    public ObjectValue(int nFields) {
        concrete = new Value[nFields];
        address = -1;
    }

    public ObjectValue(int i, int v) {
        concrete = null;
        address = v;
//        symbolic = (address==0?0:-1);
    }


    public IntValue IF_ACMPEQ(ObjectValue o2) {
        boolean result = this==o2;
        return new IntValue(result?1:0, null);
    }

    public IntValue IF_ACMPNE(ObjectValue o2) {
        boolean result = this!=o2;
        return new IntValue(result?1:0, null);
    }

    public IntValue IFNULL() {
        boolean result = this.address==0;
        return new IntValue(result?1:0, null);
    }

    public IntValue IFNONNULL() {
        boolean result = this.address!=0;
        return new IntValue(result?1:0, null);
    }

    public Value getField(int fieldId) {
        if (address==0) throw new NullPointerException("User NullPointerException");
        if (concrete==null) return PlaceHolder.instance;
        Value v = concrete[fieldId];
        if (v==null) return PlaceHolder.instance;
        else return v;
    }

    public void setField(int fieldId, Value value) {
        if (address==0) throw new NullPointerException("User NullPointerException");
        if (concrete!=null)
            concrete[fieldId] = value;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public Value invokeMethod(String name, Value[] args) {
        return PlaceHolder.instance;
    }
}
