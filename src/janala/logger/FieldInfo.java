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

package janala.logger;

import janala.interpreters.ClassDepot;

import java.io.Serializable;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/3/12
 * Time: 5:46 PM
 */
public class FieldInfo implements Serializable {
    String className;
    String fieldName;
    boolean isStatic;

    private int fieldId;

    public FieldInfo(String className, String fieldName, boolean aStatic) {
        this.className = className.replace('/','.');
        this.fieldName = fieldName;
        isStatic = aStatic;
        fieldId = -1;
    }

    public FieldInfo init() {
        if (fieldId == -1) {
            if (isStatic) {
                fieldId = ClassDepot.instance.getStaticFieldIndex(className,fieldName);
            } else {
                fieldId = ClassDepot.instance.getFieldIndex(className,fieldName);
            }
        }
        return this;
    }

    @Override
    public String toString() {
        return "FieldInfo{" +
                "className='" + className + '\'' +
                ", fieldName='" + fieldName + '\'' +
                ", isStatic=" + isStatic +
                ", fieldId=" + fieldId +
                '}';
    }

    public int getFieldId() {
        if (fieldId == -1) {
            if (isStatic) {
                fieldId = ClassDepot.instance.getStaticFieldIndex(className,fieldName);
            } else {
                fieldId = ClassDepot.instance.getFieldIndex(className,fieldName);
            }
        }
        return fieldId;
    }
}
