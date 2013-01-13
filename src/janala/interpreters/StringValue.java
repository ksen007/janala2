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
    private SymbolicStringExpression symbolic;

    public StringValue(String string, int address) {
        super(100,address);
        this.string = string;
    }

    public StringValue(String string, SymbolicStringExpression symbolic) {
        super(100, -1);
        this.string = string;
        this.symbolic = symbolic;
    }

    @Override
    public String getConcrete() {
        return string;
    }

    public SymbolicStringExpression getSymbolic() {
        return symbolic;
    }

    @Override
    public Value invokeMethod(String name, Value[] args) {
        if (name.equals("equals") && args.length == 1) {
            if (args[0] instanceof StringValue) {
                StringValue other = (StringValue)args[0];
                boolean result = string.equals(other.string);
                if (symbolic !=null && other.symbolic !=null) {
//                    SymbolicInt ret = symbolic.subtract(other.symbolic);
                    return new IntValue(result?1:0,new SymbolicStringPredicate(SymbolicStringPredicate.COMPARISON_OPS.EQ,symbolic,other.symbolic));
                } else if (symbolic != null) {
                    return new IntValue(result?1:0,new SymbolicStringPredicate(SymbolicStringPredicate.COMPARISON_OPS.EQ,symbolic,other.string));
                } else if (other.symbolic!=null) {
//                    SymbolicInt ret = other.symbolic.subtract(StringConstants.instance.get(string));
                    return new IntValue(result?1:0,new SymbolicStringPredicate(SymbolicStringPredicate.COMPARISON_OPS.EQ,string,other.symbolic));
                } else {
                    return new IntValue(result?1:0);
                }
            }
        } else if (name.equals("concat") && args.length == 1) {
            if (args[0] instanceof StringValue) {
                StringValue other = (StringValue)args[0];
                String result = string.concat(other.string);
                if (symbolic !=null && other.symbolic !=null) {
                    return new StringValue(result, symbolic.concat(other.symbolic));
                } else if (symbolic != null) {
                    return new StringValue(result, symbolic.concatStr(other.string));
                } else if (other.symbolic!=null) {
                    return new StringValue(result,other.symbolic.concatToStr(string));
                } else {
                    return new StringValue(result, null);
                }
            }
        } else if (name.equals("length")) {
            int result = string.length();
            if (symbolic != null) {
                return symbolic.getField("length");
            } else {
                return new IntValue(result);
            }
        }
        return super.invokeMethod(name, args);
    }

    public int MAKE_SYMBOLIC() {
        IntValue length = new IntValue(string.length());
        int ret = symbol;
        symbolic = new SymbolicStringExpression(symbol++, length);
        length.MAKE_SYMBOLIC();
        //System.out.println("String symbol x"+ret+" = \""+string+"\"");
        return ret;
    }
}
