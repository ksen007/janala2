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
