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
import janala.interpreters.PlaceHolder;
import janala.interpreters.Value;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/3/12
 * Time: 5:53 PM
 */
public class ObjectInfo implements Serializable {
    Map<String, Integer> fieldNameToIndex;
    ArrayList<FieldInfo> fieldList;

    Map<String, Integer> staticFieldNameToIndex;
    ArrayList<FieldInfo> staticFieldList;

    int nStaticFields;
    public int nFields;
    public Value[] statics;

    public String className;

    public ObjectInfo(String className) {
        this.className = className.replace('/','.');
        nFields = -1;
    }

    private int get(String className,String fieldName, boolean isStatic, Map<String, Integer> fieldNameToIndex, ArrayList<FieldInfo> fieldList) {
        //System.out.println("******************* calling get *******************************");
        if (fieldNameToIndex==null) {
            fieldNameToIndex = new TreeMap<String, Integer>();
            if (isStatic) {
                this.staticFieldNameToIndex = fieldNameToIndex;
            } else {
                this.fieldNameToIndex = fieldNameToIndex;
            }
        }
        if (fieldList==null) {
            fieldList = new ArrayList<FieldInfo>();
            if (isStatic) {
                this.staticFieldList = fieldList;
            } else {
                this.fieldList = fieldList;
            }
        }
        Integer i = fieldNameToIndex.get(fieldName);
        if (i==null) {
            i = fieldList.size();
            fieldNameToIndex.put(fieldName,i);
            fieldList.add(new FieldInfo(className,fieldName,isStatic));
        }
//        System.out.println("ObjectInfo "+this);
        return i;
    }

    public int get(String className,String fieldName, boolean isStatic) {
        if (isStatic) return get(className,fieldName,isStatic,staticFieldNameToIndex,staticFieldList);
        else return get(className,fieldName,isStatic,fieldNameToIndex,fieldList);
    }

    public FieldInfo get(int i, boolean isStatic) {
        if (isStatic) return staticFieldList.get(i);
        else return fieldList.get(i);
    }

    public ObjectInfo init() {
        if (nFields==-1) {
            nFields = ClassDepot.instance.nFields(className);
            nStaticFields = ClassDepot.instance.nStaticFields(className);
            if (fieldList!=null)
                for (FieldInfo fieldInfo : fieldList) {
                    fieldInfo.init();
                }
            if (staticFieldList!=null)
                for (FieldInfo fieldInfo : staticFieldList) {
                    fieldInfo.init();
                }
        }
        statics = new Value[nStaticFields];
        return this;
    }

    public Value getStaticField(int fieldId) {
        Value v = statics[fieldId];
        if (v==null) return PlaceHolder.instance;
        else return v;
    }

    public void setField(int fieldId, Value value) {
        statics[fieldId] = value;
    }

    @Override
    public String toString() {
        return "ObjectInfo{" +
                "fieldNameToIndex=" + fieldNameToIndex +
                ", fieldList=" + fieldList +
                ", staticFieldNameToIndex=" + staticFieldNameToIndex +
                ", staticFieldList=" + staticFieldList +
                ", nStaticFields=" + nStaticFields +
                ", nFields=" + nFields +
                ", statics=" + (statics == null ? null : Arrays.asList(statics)) +
                ", className='" + className + '\'' +
                '}';
    }
}
