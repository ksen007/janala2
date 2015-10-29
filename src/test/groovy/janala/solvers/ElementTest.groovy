package janala.solvers

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue

import org.junit.Test

class ElementTest {
  @Test
  void testBranchElement() {
    BranchElement e = new BranchElement(false, false, 0, 0)
    assertTrue(e.toString().contains("BranchElement"))
    assertEquals(0, e.getIid())
  }

  @Test
  void testMethodElement() {
    MethodElement e = new MethodElement(false, 0)
    assertTrue(e.toString().contains("MethodElement"))
    assertEquals(0, e.getIid())
  }
} 