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

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */
public class ArrayBasedTuple {
    public Object[] array;
    private int hc;
    private boolean isHcInitialized = false;

    public ArrayBasedTuple(Object[] array) {
        this.array = array;
    }

    @Override
    public int hashCode() {
        if (!isHcInitialized) {
            hc = 0;
            for (int i = 0; i < array.length; i++) {
                Object o = array[i];
                hc += o.hashCode();
            }
            isHcInitialized = true;
        }
        return hc;
    }

    public void invalidateHashCode() {
        isHcInitialized = false;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ArrayBasedTuple)) return false;
        ArrayBasedTuple other = (ArrayBasedTuple)o;
        if (array==other.array) return true;
        if (other.array==null || array==null) return false;
        if (other.array.length != array.length) return false;
        for (int i = 0; i < array.length; i++) {
            if (!array[i].equals(other.array[i])) return false;
        }
        return true;
    }
}
