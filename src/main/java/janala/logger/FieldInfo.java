package janala.logger;

import janala.interpreters.ClassDepot;

import java.io.Serializable;

public class FieldInfo implements Serializable {
  private final String className;
  private final String fieldName;
  private final boolean isStatic;
  private final ClassDepot classDepot;
  private int fieldId;

  public FieldInfo(String className, String fieldName, boolean aStatic, ClassDepot classDepot) {
    this.className = className.replace('/', '.');
    this.fieldName = fieldName;
    isStatic = aStatic;
    fieldId = -1;
    this.classDepot = classDepot;
  }

  @Override
  public String toString() {
    return "FieldInfo{"
        + "className='"
        + className
        + '\''
        + ", fieldName='"
        + fieldName
        + '\''
        + ", isStatic="
        + isStatic
        + ", fieldId="
        + fieldId
        + '}';
  }

  public int getFieldId() {
    if (fieldId == -1) {
      if (isStatic) {
        fieldId = classDepot.getStaticFieldIndex(className,fieldName);
      } else {
        fieldId = classDepot.getFieldIndex(className,fieldName);
      }
    }
    return fieldId;    
  }
}
