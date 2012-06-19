/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/3/12
 * Time: 5:53 PM
 */
public class ObjectInfo implements Serializable {
    Map<String, Integer> nameToIndex;
    ArrayList<FieldInfo> fieldList;

    int nStaticFields;
    int nFields;

    String className;

    public ObjectInfo(String className) {
        this.className = className.replace('/','.');
        nFields = -1;
    }

    public int get(String className,String fieldName, boolean isStatic) {
        if (nameToIndex==null) {
            nameToIndex = new TreeMap<String, Integer>();
        }
        if (fieldList==null) {
            fieldList = new ArrayList<FieldInfo>();
        }
        Integer i = nameToIndex.get(fieldName);
        if (i==null) {
            i = fieldList.size();
            nameToIndex.put(fieldName,i);
            fieldList.add(new FieldInfo(className,fieldName,isStatic));
        }
        return i;
    }

    public FieldInfo get(int i) {
        return fieldList.get(i);
    }

    public ObjectInfo init() {
        if (nFields==-1) {
            nFields = ClassDepot.instance.nFields(className);
            nStaticFields = ClassDepot.instance.nStaticFields(className);
            if (fieldList!=null)
                for (FieldInfo fieldInfo : fieldList) {
                    fieldInfo.init();
                }
        }
        return this;
    }

}
