package janala.logger

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue

import janala.interpreters.ClassDepot
import org.junit.Test

import groovy.transform.CompileStatic

@CompileStatic
class FieldInfoTest {
  @CompileStatic
  class FakeClassDepot extends ClassDepot {
    @Override
    public int getFieldIndex(String cNam, String field) {
      1
    }

    @Override
    public int getStaticFieldIndex(String cname, String field) {
      1
    }
  }
  @Test
  void testField() {
    ClassDepot cd = new FakeClassDepot()
    FieldInfo fi = new FieldInfo("class", "field", true, cd)
    assertEquals(1, fi.getFieldId())


    FieldInfo f = new FieldInfo("class", "field", false, cd)
    assertEquals(1, f.getFieldId())

    assertTrue(f.toString().contains("FieldInfo"))
  }   
}