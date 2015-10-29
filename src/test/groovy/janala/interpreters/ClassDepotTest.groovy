package janala.interpreters

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue

import org.junit.Test

import groovy.transform.CompileStatic


@CompileStatic
class TestClassForDepot {
  private String id;
  public static sId;
}


@CompileStatic
class ClassDepotTest {
  @Test
  void testInit() {
    ClassDepot a = new ClassDepot()

    ClassDepot.setInstance(a)
    assertEquals(a, ClassDepot.getInstance())
  }

  @Test
  void testCreateTemplate() {
    ClassDepot a = new ClassDepot()
    int ct = a.nFields("janala.interpreters.TestClassForDepot")
    println("Test class has " + ct + " fields")
    assertTrue(ct > 0)

    int st = a.nStaticFields("janala.interpreters.TestClassForDepot")
    println("Test class has " + st + " static fields")
    assertTrue(st > 0)

    int idx = a.getFieldIndex("janala.interpreters.TestClassForDepot", "id")
    println("id is " + idx + " field")
    assertTrue(idx >= 0)
    int sIdx = a.getStaticFieldIndex(
      "janala.interpreters.TestClassForDepot", "sId")
    println("sId is " + sIdx + " static field")
    assertTrue(sIdx >= 0)
  }

  @Test
  void testSubClassShouldHaveSuperClassFields() {
    ClassDepot a = new ClassDepot()
    int subIdx = a.getFieldIndex("janala.interpreters.TestSubClass", "a")
    assertTrue(subIdx < 2)

    int superIdx = a.getFieldIndex("janala.interpreters.TestClass", "a")
    assertEquals(subIdx, superIdx)

    subIdx = a.getFieldIndex("janala.interpreters.TestSubClass", "subA")
    assertEquals(2, subIdx)
  }
}