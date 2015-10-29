package janala.instrument

import static org.junit.Assert.assertEquals

import org.junit.Test
import groovy.transform.CompileStatic

@CompileStatic
class InstrumentGlobalStateTest {

  @Test
  void testIds() {
    def a = new GlobalStateForInstrumentation()
    a.setCid(1)
    assertEquals(0, a.getMid())
    assertEquals(1, a.getCid())

    assertEquals(Integer.parseInt("1000000000000000001", 2), 
      a.incAndGetId())
    assertEquals(Integer.parseInt("100000000", 2), 
      GlobalStateForInstrumentation.getCidMid(a.getCid(), a.getMid()))

    a.incMid()
    assertEquals(1, a.getMid())
    assertEquals(1, a.getCid())
  }
}
