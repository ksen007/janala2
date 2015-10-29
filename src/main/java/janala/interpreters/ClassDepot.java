package janala.interpreters;

import janala.utils.MyLogger;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ClassDepot implements Serializable {

  final private Map<String, ClassTemplate> templates;

  public static ClassDepot instance = new ClassDepot();
  public static void setInstance(ClassDepot i) {
    instance = i;
  }
  public static ClassDepot getInstance() {
    return instance;
  }

  private final static Logger logger = MyLogger.getLogger(ClassDepot.class.getName());

  // VisibleForTesting
  public ClassDepot() {
    templates = new TreeMap<String, ClassTemplate>();
  }

  private ClassTemplate getOrCreateTemplate(String cName, Class<?> clazz) {
    ClassTemplate ct = templates.get(cName);
    if (ct != null) {
      return ct;
    }
    ct = new ClassTemplate(clazz);
    templates.put(cName, ct);
    Class<?> parent = clazz.getSuperclass();
    if (parent != null) {
      ClassTemplate pt = getOrCreateTemplate(parent.getName(), parent);
      ct.addFields(pt);
    }
    return ct;
  }

  public int getFieldIndex(String cName, String field) {
    try {
      Class<?> clazz = Class.forName(cName);
      ClassTemplate ct = getOrCreateTemplate(cName, clazz);
      return ct.getFieldIndex(field);
    } catch (ClassNotFoundException e) {
      logger.log(Level.SEVERE, "", e);
      return -1;
    }
  }

  public int getStaticFieldIndex(String cName, String field) {
    try {
      Class<?> clazz = Class.forName(cName);
      ClassTemplate ct = getOrCreateTemplate(cName, clazz);
      return ct.getStaticFieldIndex(field);
    } catch (ClassNotFoundException e) {
      logger.log(Level.SEVERE, "", e);
      return -1;
    }
  }

  public int nFields(String cName) {
    try {
      Class<?> clazz = Class.forName(cName);
      ClassTemplate ct = getOrCreateTemplate(cName, clazz);
      return ct.nFields();
    } catch (ClassNotFoundException e) {
      logger.log(Level.SEVERE, "Class not found", e);
      return -1;
    }
  }

  public int nStaticFields(String cName) {
    try {
      Class<?> clazz = Class.forName(cName);
      ClassTemplate ct = getOrCreateTemplate(cName, clazz);
      return ct.nStaticFields();
    } catch (ClassNotFoundException e) {
      logger.log(Level.SEVERE, "", e);
      return -1;
    }
  }
}
