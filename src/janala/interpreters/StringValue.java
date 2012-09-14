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
 * Time: 12:12 PM
 */
public class StringValue extends ObjectValue {
    private String string;
    private SymbolicInt symbolic;

    public StringValue(String string, int address) {
        super(100,address);
        this.string = string;
    }

    @Override
    public String getConcrete() {
        return string;
    }

    @Override
    public Value invokeMethod(String name, Value[] args) {
        if (name.equals("equals") && args.length == 1) {
            if (args[0] instanceof StringValue) {
                StringValue other = (StringValue)args[0];
                boolean result = string.equals(other.string);
                if (symbolic !=null && other.symbolic !=null) {
                    SymbolicInt ret = symbolic.subtract(other.symbolic);
                    return new IntValue(result?1:0,ret!=null?ret.setop(SymbolicInt.COMPARISON_OPS.EQ):null);
                } else if (symbolic != null) {
                    IntValue ret = new IntValue(result?1:0,symbolic.subtract(StringConstants.instance.get(other.string)).setop(SymbolicInt.COMPARISON_OPS.EQ));
                    return ret;
                } else if (other.symbolic!=null) {
                    SymbolicInt ret = other.symbolic.subtract(StringConstants.instance.get(string));
                    return new IntValue(result?1:0,ret.setop(SymbolicInt.COMPARISON_OPS.EQ));
                } else {
                    return new IntValue(result?1:0,null);
                }
            }
        }
        return super.invokeMethod(name, args);
    }

    public void MAKE_SYMBOLIC(int symbol) {
        symbolic = new SymbolicInt(symbol);
    }
}
