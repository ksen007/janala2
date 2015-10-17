package janala.interpreters;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

public class ClassTemplate {
  private final ArrayList<String> fields;
  private final ArrayList<String> staticFields;

  private void populateAllFields(Class c) {
    for (Field field: c.getDeclaredFields()) {
      if (!Modifier.isStatic(field.getModifiers())) {
        addField(field.getName());
      } else {
        addStaticField(field.getName());
      }
    }
  }

  public ClassTemplate(Class c) {
    fields = new ArrayList<String>();
    staticFields = new ArrayList<String>();
    populateAllFields(c);
  }

  public String toString() {
    return fields.toString();
  }
  
  private void addField(String name) {
    fields.add(name);
   }

  public void addFields(ClassTemplate pt) {
    fields.addAll(0, pt.fields);
    staticFields.addAll(0, pt.staticFields);
  }

  private void addStaticField(String name) {
    staticFields.add(name);
  }

  public int getFieldIndex(String name) {
    return fields.indexOf(name);
  }

  public int getStaticFieldIndex(String name) {
    return staticFields.indexOf(name);
  }

  public int nFields() {
    return fields.size();
  }

  public int nStaticFields() {
    return staticFields.size();
  }
}
