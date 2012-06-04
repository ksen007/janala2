package janala.analysis;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/3/12
 * Time: 5:50 PM
 */
public class ClassNames {
    Map<String, Integer> nameToIndex;
    ArrayList<ObjectInfo> classList;

    public final static ClassNames instance = new ClassNames();

    public int get(String className) {
        if (nameToIndex==null) {
            nameToIndex = new TreeMap<String, Integer>();
        }
        if (classList==null) {
            classList = new ArrayList<ObjectInfo>();
        }
        Integer i = nameToIndex.get(className);
        if (i==null) {
            i = classList.size();
            nameToIndex.put(className,i);
            classList.add(new ObjectInfo(className));
        }
        return i;
    }

    public ObjectInfo get(int i) {
        return classList.get(i);
    }

}
