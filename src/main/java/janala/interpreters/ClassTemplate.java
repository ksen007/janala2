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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.LinkedList;

public class ClassTemplate {
    LinkedList<String> fields;
    LinkedList<String> staticFields;
    //SValue staticFields[];

    public void populateAllFields(Class c) {
        Field[] fields = c.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            if (!Modifier.isStatic(field.getModifiers()))
                addField(field.getName());
            else {
                addStaticField(field.getName());
            }
        }
//        createStaticFields();
        //System.out.println(this.fields);
        //System.out.println(this.staticFields);
    }

    public ClassTemplate() {
        fields = new LinkedList<String>();
        staticFields = new LinkedList<String>();
    }

    private void addField(String name) {
        fields.addLast(name);
        // System.out.println("Adding field "+name);
    }

    public void addFields(ClassTemplate pt) {
        fields.addAll(pt.fields);
        staticFields.addAll(pt.staticFields);
    }

    private void addStaticField(String name) {
        staticFields.addLast(name);
    }

//    private void createStaticFields() {
//        staticFields = new SObject[staticFieldToIndex.size()];
//    }

    public int getFieldIndex(String name) {
        int i = fields.size();
        Iterator<String> iter = fields.descendingIterator();
        while(iter.hasNext()) {
            i--;
            String field = iter.next();
            if (field.equals(name))
                return i;
        }
        return -1;
    }

    public int getStaticFieldIndex(String name) {
        int i = staticFields.size();
        Iterator<String> iter = staticFields.descendingIterator();
        while(iter.hasNext()) {
            i--;
            String field = iter.next();
            if (field.equals(name))
                return i;
        }
        return -1;
    }

    public int nFields() {
        return fields.size();
    }

    public int nStaticFields() {
        return staticFields.size();
    }
}
