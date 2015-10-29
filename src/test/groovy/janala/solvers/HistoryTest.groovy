package janala.solvers

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNull
import static org.junit.Assert.assertTrue
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.verify
import static org.mockito.Mockito.when
import static org.mockito.Matchers.eq

import janala.interpreters.IntValue
import janala.interpreters.Constraint
import janala.interpreters.SymbolicInt
import janala.utils.FileUtil
import janala.config.Config
import org.junit.Test
import org.junit.Before


import groovy.transform.CompileStatic

@CompileStatic
class HistoryTest {
  FileUtil fileUtil
  History history
  Solver solver // A mock solver

  @Before
  void setup() {
    fileUtil = mock(FileUtil.class)
    solver = mock(Solver.class)
    history = new History(solver, fileUtil, new Config())
  }


  @Test
  void testInit() {
    history.beginScope(0)
    history.endScope(0)

    history.setIndex(1)
    history.beginScope(1)
    assertEquals(1, history.skip)
    history.endScope(1)
    assertEquals(0, history.skip)
    assertEquals(4, history.getHistory().size())
  }

  @Test
  void testInitRepeat() {
    history.beginScope(0)
    history.endScope(0)

    history.setIndex(0)
    history.beginScope(1)
    history.endScope(1)
    assertEquals(2, history.getHistory().size())
  }

  @Test
  void testRemoveLastBranch() {
    def branchElement = new BranchElement(false, false, -1, 0)
    
    history.getHistory().add(branchElement)
    history.setIndex(1)

    assertNull(history.removeLastBranch())
  }

  @Test
  void testSetBranch() {
    // Unconditionally set to true branch
    history.checkAndSetBranch(true, null, 0)
    assertEquals(1, history.getHistory().size())
    assertEquals(0, history.getPathConstraint().size())
  }

  @Test
  void testSetBranchWithConstraint() {
    Constraint c = new SymbolicInt(1)
    history.checkAndSetBranch(true, c, 0)
    assertEquals(1, history.getHistory().size())
    assertEquals(1, history.getPathConstraint().size())

    history.setIndex(0) 
    history.checkAndSetBranch(true, c, 0)
    assertEquals(1, history.getHistory().size())
    assertEquals(2, history.getPathConstraint().size())

    history.setIndex(0) 
    history.checkAndSetBranch(false, c, 0)
    println(history.getPathConstraint())
    assertEquals(1, history.getHistory().size())
    assertEquals(3, history.getPathConstraint().size())

    history.setIndex(0)
    history.ignore() // Insert into history
    history.checkAndSetBranch(false, c, 0)
    println(history.getPathConstraint())
    assertEquals(2, history.getHistory().size())
    assertEquals(4, history.getPathConstraint().size())
  }

  @Test
  void testSolveAt() {
    Constraint c = new SymbolicInt(1)
    history.checkAndSetBranch(true, c, 0)

    when(solver.solve()).thenReturn(true)
    history.solveAt(0)
    
    verify(solver).setInputs(new LinkedList<InputElement>())
    verify(solver).setPathConstraint(history.getPathConstraint())
    verify(solver).setPathConstraintIndex(0)
    verify(solver).solve()
  }

  @Test
  void testSolveAtWithHead() {
    // The first constraint will be included since it has a path constraint.
    Constraint c = new SymbolicInt(1)
    history.checkAndSetBranch(true, c, 0) 
    history.checkAndSetBranch(true, c, 0)

    when(solver.solve()).thenReturn(true)
    history.solveAt(0, 1)

    verify(solver).setInputs(new LinkedList<InputElement>())
    verify(solver).setPathConstraint(history.getPathConstraint())
    verify(solver).setPathConstraintIndex(1)
    verify(solver).solve()
  }

  @Test
  void testSetLastBranchDone() {
    Constraint c = new SymbolicInt(1)
    history.checkAndSetBranch(true, c, 0) 
    history.setLastBranchDone()

    List<Element> h = history.getHistory()
    BranchElement last = (BranchElement) h.get(h.size() - 1)
    assertTrue(last.done)
  }

  @Test
  void testSetLastForceTruth() {
    Constraint c = new SymbolicInt(1)
    history.checkAndSetBranch(true, c, 0) 
    history.setLastForceTruth()

    List<Element> h = history.getHistory()
    BranchElement last = (BranchElement) h.get(h.size() - 1)
    assertTrue(last.isForceTruth)
  }

  @Test
  void testCleanup() {
    // The first constraint will be included since it has a path constraint.
    Constraint c = new SymbolicInt(1)
    history.checkAndSetBranch(true, c, 0) 
    history.checkAndSetBranch(true, c, 0)
    history.cleanup(0)

    assertEquals(1, history.getHistory().size())
  }

  @Test
  void testSerialization() {
    Constraint c = new SymbolicInt(1)
    history.checkAndSetBranch(true, c, 0) 

    def os = new ByteArrayOutputStream()
    history.writeHistory(os)
    assertTrue(os.size() > 10)

    def istream = new ByteArrayInputStream(os.toByteArray())
    def readHistory = History.readHistory(solver, istream)

    assertEquals(history.getHistory().toString(), 
      readHistory.getHistory().toString())
  }
  
  @Test
  void testBackup() {
    Constraint c = new SymbolicInt(1)
    history.checkAndSetBranch(true, c, 0)
    history.setLastBranchDone()
    def os = new ByteArrayOutputStream()
    history.writeHistory(os)
    
    def istream = new ByteArrayInputStream(os.toByteArray())
    def ostream = new ByteArrayOutputStream()
    
    History.createBacktrackHistory(0, istream, ostream)
    assertEquals(os.toByteArray(), ostream.toByteArray())
  }
}