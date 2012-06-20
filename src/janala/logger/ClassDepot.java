/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.logger;

import java.util.Map;
import java.util.TreeMap;

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
