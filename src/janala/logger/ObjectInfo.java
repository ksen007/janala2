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

    String className;

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
