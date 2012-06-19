/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.logger;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class ClassTemplate {
    LinkedList<String> fields;
    LinkedHashMap<String,Integer> staticFieldToIndex;
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
        staticFieldToIndex = new LinkedHashMap<String,Integer>();
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
