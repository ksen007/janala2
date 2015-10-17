package janala.interpreters;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/27/12
 * Time: 6:57 PM
 */
public class ObjectFactory {
  public static ObjectValue create(int nFields, String className) {
    if (className.equals("java.lang.Integer")) {
      return new IntegerObjectValue();
    } else if (className.equals("java.lang.Long")) {
      return new LongObjectValue();
    } else if (className.equals("java.sql.Date")
        || className.equals("java.sql.Time")
        || className.equals("java.sql.Timestamp")
        || className.equals("java.util.Date")) {
      return new SqlDateObjectValue();
    }
    return new ObjectValue(nFields);
  }
}
