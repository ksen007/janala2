package janala.instrument

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue

import org.junit.Test

import groovy.transform.CompileStatic 

/**
 * Test class for coverage
 */
@CompileStatic
class CoverageTest {
  
  @Test
  void testReadWrite() {
    Coverage cov = new Coverage()
    cov.addBranchCount(0)
    cov.visitBranch(0, true) // Make sure it is non-empty
    cov.commitBranches(false)
    
    OutputStream bytes = new ByteArrayOutputStream()
    cov.write(bytes)
    def ins = new ByteArrayInputStream(bytes.toByteArray())
    Coverage cov2 = Coverage.parse(ins)
    assertEquals(cov, cov2)
  }
  
  @Test
  void testToString() {
    Coverage cov = new Coverage()
    cov.addBranchCount(0)
    cov.visitBranch(0, true) // Make sure it is non-empty
    cov.commitBranches(false)
    assertTrue(cov.toString().length() > 10)
  }
  
  @Test
  void testPrint() {
    Coverage cov = new Coverage()
    GlobalStateForInstrumentation.instance.setCid(0)
    cov.getCid("MyClass")
    cov.setLastMethod("MyMethod")
    cov.setCidmidToName(GlobalStateForInstrumentation.getCidMid(0, 0))
    int iid = GlobalStateForInstrumentation.instance.getId()
    cov.addBranchCount(0)
    cov.visitBranch(iid, true)
    cov.commitBranches(true)
    
    def bytes = new ByteArrayOutputStream()
    cov.printCoverage(new PrintStream(bytes))
    String s = bytes.toString()
    assertTrue(s.contains("Branch coverage"))
  }
}

