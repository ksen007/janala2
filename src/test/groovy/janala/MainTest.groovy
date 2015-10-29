package janala

import static org.junit.Assert.assertEquals

import janala.utils.Inputs
import org.junit.Test

import groovy.transform.CompileStatic

@CompileStatic
class MainTest {
  @Test
  void testReadInt() {
    Inputs inputs = new Inputs(["1"])
    Main.setInput(inputs)
    assertEquals(1, Main.readInt(2))
    assertEquals(2, Main.readInt(2)) // Next one does not exists
  }

}

