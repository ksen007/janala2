package janala.logger

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue

import janala.interpreters.ClassDepot
import janala.interpreters.IntValue
import org.junit.Test

import groovy.transform.CompileStatic

@CompileStatic
class ObjectInfoTest {
	class FakeClassDepot extends ClassDepot {
    @Override
    public int getFieldIndex(String cNam, String field) {
      0
    }

    @Override
    public int getStaticFieldIndex(String cname, String field) {
      0
    }

    @Override
    public int nFields(String cname) { 1 }

    @Override
    public int nStaticFields(String cname) { 1 }
  }

  @Test
  void testInit() {
    ClassDepot classDepot = new FakeClassDepot()
    ObjectInfo info = new ObjectInfo("testClass", classDepot)
    assertEquals(info.className, "testClass")
    assertEquals(0, info.getIdx("field", true))
    assertEquals(0, info.getIdx("field", false))
    
    // This is really weird.
    // info.init(classDepot)
    assertEquals(1, info.getNFields())
    assertEquals(0, info.get(0, true).getFieldId())
    assertEquals(0, info.get(0, false).getFieldId())

    def value = new IntValue(0)
    info.setStaticField(0, value)
    assertEquals(value, info.getStaticField(0))

    println(info)
    assertTrue(info.toString().contains("ObjectInfo"))
  }
}

