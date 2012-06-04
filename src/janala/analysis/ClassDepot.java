package janala.analysis;

import java.util.Map;
import java.util.TreeMap;

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
public class ClassDepot {

    private Map<String,ClassTemplate> templates;

    public final static ClassDepot instance = new ClassDepot();

    private ClassDepot() {
        templates = new TreeMap<String, ClassTemplate>();
    }

    private ClassTemplate getOrCreateTemplate(String cName, Class clazz) {
        ClassTemplate ct = templates.get(cName);
        if (ct != null)
            return ct;
        System.out.println("Adding to parents of "+cName);
        ct = new ClassTemplate();
        templates.put(cName,ct);
        Class parent = clazz.getSuperclass();
        if (parent !=null) {
            ClassTemplate pt = getOrCreateTemplate(parent.getName(),parent);
            ct.addFields(pt);
        }
        System.out.println("Adding to "+cName);
        ct.populateAllFields(clazz);
        return ct;
    }

    public int getFieldIndex(String cName, String field) {
        try {
            Class clazz = Class.forName(cName);
            ClassTemplate ct = getOrCreateTemplate(cName,clazz);
            return ct.getFieldIndex(field);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return -1;
    }

    public int getStaticFieldIndex(String cName, String field) {
        try {
            Class clazz = Class.forName(cName);
            ClassTemplate ct = getOrCreateTemplate(cName,clazz);
            return ct.getStaticFieldIndex(field);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return -1;
    }

    public int nFields(String cName) {
        try {
            Class clazz = Class.forName(cName);
            ClassTemplate ct = getOrCreateTemplate(cName,clazz);
            return ct.nFields();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return -1;
    }

    public int nStaticFields(String cName) {
        try {
            Class clazz = Class.forName(cName);
            ClassTemplate ct = getOrCreateTemplate(cName,clazz);
            return ct.nStaticFields();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(instance.getFieldIndex("janala.scratchpad.B1","x"));
    }

    public int getClassId(String className) {
        return 0;  //To change body of created methods use File | Settings | File Templates.
    }
}
