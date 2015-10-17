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
 * Time: 10:01 AM
 */
public class FloatValue extends Value{
    float concrete;

    @Override
    public Object getConcrete() {
        return concrete;
    }

    public FloatValue(float concrete) {
        this.concrete = concrete;
    }

    public FloatValue FADD(FloatValue d) {
        return new FloatValue(concrete+d.concrete);
    }

    public FloatValue FSUB(FloatValue d) {
        return new FloatValue(concrete-d.concrete);
    }

    public FloatValue FMUL(FloatValue d) {
        return new FloatValue(concrete*d.concrete);
    }

    public FloatValue FDIV(FloatValue d) {
        return new FloatValue(concrete/d.concrete);
    }

    public FloatValue FREM(FloatValue d) {
        return new FloatValue(concrete%d.concrete);
    }

    public FloatValue FNEG() {
        return new FloatValue(-concrete);
    }

    public IntValue F2I() {
        return new IntValue((int)concrete);
    }

    public LongValue F2L() {
        return new LongValue((long)concrete);
    }

    public DoubleValue F2D() {
        return new DoubleValue((double)concrete);
    }

    public IntValue FCMPL(FloatValue d) {
        if (Float.isNaN(concrete) || Float.isNaN(d.concrete)) {
            return new IntValue(-1);
        } else if (concrete == d.concrete) {
            return new IntValue(0);
        } else if (concrete > d.concrete) {
            return new IntValue(1);
        } else {
            return new IntValue(-1);
        }

    }

    public IntValue FCMPG(FloatValue d) {
        if (Float.isNaN(concrete) || Float.isNaN(d.concrete)) {
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
