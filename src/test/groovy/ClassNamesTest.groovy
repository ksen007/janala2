package janala.logger

import static org.junit.Assert.assertTrue
import static org.junit.Assert.assertEquals

import janala.interpreters.ClassDepot
import org.junit.Test

import groovy.transform.CompileStatic

@CompileStatic
class ClassNamesTest {
  ClassDepot classDepot = new ClassDepot() {

    @Override
    public int getFieldIndex(String cNam, String field) { 0 }

    @Override
    public int getStaticFieldIndex(String cname, String field) { 0 }

    @Override
    public int nFields(String cname) { 1 }

    @Override
    public int nStaticFields(String cname) { 1 }
  }

  @Test
  void testGet() {
    ClassNames cn = new ClassNames(classDepot)
    int i = cn.get("MyTestClass")
    assertEquals(0, i)
    
    // cn.init(classDepot)
    ObjectInfo oi = cn.get(0)
    assertEquals(1, oi.getNFields())

    assertTrue(cn.toString().contains("ClassNames"))
  }
}