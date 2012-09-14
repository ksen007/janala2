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

import janala.utils.MyLogger;

import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClassDepot {

    private Map<String,ClassTemplate> templates;

    public final static ClassDepot instance = new ClassDepot();
    private final static Logger logger = MyLogger.getLogger(ClassDepot.class.getName());

    private ClassDepot() {
        templates = new TreeMap<String, ClassTemplate>();
    }

    private ClassTemplate getOrCreateTemplate(String cName, Class clazz) {
        ClassTemplate ct = templates.get(cName);
        if (ct != null)
            return ct;
        //System.out.println("Adding to parents of "+cName);
        ct = new ClassTemplate();
        templates.put(cName,ct);
        Class parent = clazz.getSuperclass();
        if (parent !=null) {
            ClassTemplate pt = getOrCreateTemplate(parent.getName(),parent);
            ct.addFields(pt);
        }
        //System.out.println("Adding to "+cName);
        ct.populateAllFields(clazz);
        return ct;
    }

    public int getFieldIndex(String cName, String field) {
        try {
            Class clazz = Class.forName(cName);
            ClassTemplate ct = getOrCreateTemplate(cName,clazz);
            return ct.getFieldIndex(field);
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE,"",e);
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
            logger.log(Level.SEVERE,"",e);
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
            logger.log(Level.SEVERE,"",e);
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
            logger.log(Level.SEVERE,"",e);
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
