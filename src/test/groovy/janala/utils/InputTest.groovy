package janala.utils

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertFalse
import static org.junit.Assert.assertTrue

import org.junit.Test
import org.junit.Before

import groovy.transform.CompileStatic

import janala.config.Config

@CompileStatic
class InputsTest {
  Inputs data
  
  @Before
  void setup() {
    data = new Inputs(["1", "2"])
  }
  
  @Test
  void testRead() {
    def x = data.read()
    assertEquals("1", x)
    assertTrue(data.isInputAvailable())
    assertFalse(data.isBeginScope())
    assertFalse(data.isEndScope())
  }
  
  @Test
  void testScope() {
    data = new Inputs([Config.instance.scopeBeginMarker, Config.instance.scopeEndMarker])
    assertTrue(data.isBeginScope())
    data.next()
    assertTrue(data.isEndScope())
  }
  
}

