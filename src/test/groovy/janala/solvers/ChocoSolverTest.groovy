package janala.solvers

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertFalse
import static org.junit.Assert.assertTrue

import janala.config.Config
import janala.interpreters.Constraint;
import janala.interpreters.COMPARISON_OPS;
import janala.interpreters.IntValue;
import janala.interpreters.SymbolicInt

import org.junit.Test
import org.junit.Before

import groovy.transform.CompileStatic

@CompileStatic
class ChocoSolverTest {
  @Before
  void setup() {
    Config.instance.printFormulaAndSolutions = true
  }

  @Test
  void testSolver() {
    SymbolicInt x1 = new SymbolicInt(1)

    InputElement input = new InputElement(1, new IntValue(0))
    x1.setOp(COMPARISON_OPS.NE)

    List<Constraint> constraints = []
    constraints.add(x1)
    println(constraints)
    List<InputElement> inputs = [input]

    Solver solver = new ChocoSolver()
    solver.setInputs(inputs)
    solver.setPathConstraint(constraints)
    solver.visitSymbolicInt(x1)
    def x = solver.solve()
    assertTrue(x)
    def y = solver.getSolution()
    assertEquals(1, y.size())
    assertEquals("0", y.get(0))
  }
  
}