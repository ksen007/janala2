package janala.analysis;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/**
 * Copyright (c) 2006-2011,
 * Koushik Sen    <ksen@cs.berkeley.edu>
 * All rights reserved.
 * <p/>
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 * <p/>
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * <p/>
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * <p/>
 * 3. The names of the contributors may not be used to endorse or promote
 * products derived from this software without specific prior written
 * permission.
 * <p/>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER
 * OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
public class ClassTemplate {
    LinkedList<String> fields;
    Map<String,Integer> staticFieldToIndex;
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
        System.out.println(this.fields);
        System.out.println(staticFieldToIndex);
    }

    public ClassTemplate() {
        fields = new LinkedList<String>();
        staticFieldToIndex = new HashMap<String,Integer>();
    }

    private void addField(String name) {
        fields.addLast(name);
        System.out.println("Adding field "+name);
    }

    public void addFields(ClassTemplate pt) {
        fields.addAll(pt.fields);
    }

    private void addStaticField(String name) {
        staticFieldToIndex.put(name,staticFieldToIndex.size());
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
        Integer I = staticFieldToIndex.get(name);
        if (I!=null) return I;
        else return -1;
    }

    public int nFields() {
        return fields.size();
    }

    public int nStaticFields() {
        return staticFieldToIndex.size();
    }
}
