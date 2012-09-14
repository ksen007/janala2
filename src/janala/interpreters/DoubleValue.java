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
 * Time: 10:02 AM
 */
public class DoubleValue extends Value{
    double concrete;

    @Override
    public Object getConcrete() {
        return concrete;
    }

    public DoubleValue(double concrete) {
        this.concrete = concrete;
    }

    public DoubleValue DADD(DoubleValue d) {
        return new DoubleValue(concrete+d.concrete);
    }

    public DoubleValue DSUB(DoubleValue d) {
        return new DoubleValue(concrete-d.concrete);
    }

    public DoubleValue DMUL(DoubleValue d) {
        return new DoubleValue(concrete*d.concrete);
    }

    public DoubleValue DDIV(DoubleValue d) {
        return new DoubleValue(concrete/d.concrete);
    }

    public DoubleValue DREM(DoubleValue d) {
        return new DoubleValue(concrete%d.concrete);
    }

    public DoubleValue DNEG() {
        return new DoubleValue(-concrete);
    }

    public IntValue D2I() {
        return new IntValue((int)concrete);
    }

    public LongValue D2L() {
        return new LongValue((long)concrete);
    }

    public FloatValue D2F() {
        return new FloatValue((float)concrete);
    }

    public IntValue DCMPL(DoubleValue d) {
        if (Double.isNaN(concrete) || Double.isNaN(d.concrete)) {
            return new IntValue(-1);
        } else if (concrete == d.concrete) {
            return new IntValue(0);
        } else if (concrete > d.concrete) {
            return new IntValue(1);
        } else {
            return new IntValue(-1);
        }

    }

    public IntValue DCMPG(DoubleValue d) {
        if (Double.isNaN(concrete) || Double.isNaN(d.concrete)) {
            return new IntValue(1);
        } else if (concrete == d.concrete) {
            return new IntValue(0);
        } else if (concrete > d.concrete) {
            return new IntValue(1);
        } else {
            return new IntValue(-1);
        }
    }

}
