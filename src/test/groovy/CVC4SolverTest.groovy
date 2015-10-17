package janala.solvers

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.verify

import org.junit.Test
import org.junit.Before
import janala.interpreters.IntValue
import janala.interpreters.Value
import janala.interpreters.StringValue
import janala.interpreters.SymbolicInt
import janala.interpreters.SymOrInt
import janala.interpreters.SymbolicAndConstraint
import janala.interpreters.SymbolicNotConstraint
import janala.interpreters.SymbolicOrConstraint
import janala.interpreters.SymbolicFalseConstraint
import janala.interpreters.SymbolicTrueConstraint
import janala.interpreters.SymbolicIntCompareConstraint
import janala.interpreters.SymbolicStringExpression
import janala.interpreters.SymbolicStringPredicate
import janala.interpreters.SymbolicStringPredicate.STRING_COMPARISON_OPS
import janala.interpreters.COMPARISON_OPS
import janala.interpreters.Constraint
import janala.utils.FileUtil
import janala.config.Config

import groovy.transform.CompileStatic

@CompileStatic
class CVC4SolverTest {
  CVC4Solver solver
  FileUtil fileUtil
  Config config

  @Before
  void setup() {
    fileUtil = mock(FileUtil.class)
    config = new Config()
    solver = new CVC4Solver(config, fileUtil)
  }

  private void testSymbolicInt(SymbolicInt y, COMPARISON_OPS op, String expected) {
    y.setOp(op)
    def bytes = new ByteArrayOutputStream()
    def printer = new CVC4Solver.Printer(new HashSet<String>(), 
      new HashMap<String, Long>(), CVC4Solver.CONSTRAINT_TYPE.INT, new PrintStream(bytes))
    printer.print((Constraint)y)
    assertEquals(expected, bytes.toString())
  }

  @Test
  void testPrintSymbolicInt() {
    SymbolicInt x1 = new SymbolicInt(1)
    SymbolicInt x2 = new SymbolicInt(2)
    SymbolicInt y = x1.add(x2).add(1)
    testSymbolicInt(y, COMPARISON_OPS.EQ, "x1*(1) + x2*(1) + (1) = 0")
    testSymbolicInt(y, COMPARISON_OPS.NE, "x1*(1) + x2*(1) + (1) /= 0")
    testSymbolicInt(y, COMPARISON_OPS.GT, "x1*(1) + x2*(1) + (1) > 0")
    testSymbolicInt(y, COMPARISON_OPS.LT, "x1*(1) + x2*(1) + (1) < 0")
    testSymbolicInt(y, COMPARISON_OPS.GE, "x1*(1) + x2*(1) + (1) >= 0")
    testSymbolicInt(y, COMPARISON_OPS.LE, "x1*(1) + x2*(1) + (1) <= 0")
  }

  private void testSymbolicCompareInt(SymOrInt x, SymOrInt y, COMPARISON_OPS op, String expected) {
    Constraint con = new SymbolicIntCompareConstraint(x, y, op)
    def bytes = new ByteArrayOutputStream()
    def printer = new CVC4Solver.Printer(new HashSet<String>(), 
      new HashMap<String, Long>(), CVC4Solver.CONSTRAINT_TYPE.INT, new PrintStream(bytes))
    printer.print(con)
    assertEquals(expected, bytes.toString())
  }

  @Test
  void testPrintSymbolicCompareInt() {
    SymOrInt x1 = new SymOrInt("x_1")
    SymOrInt x2 = new SymOrInt(1)
    testSymbolicCompareInt(x1, x2, COMPARISON_OPS.EQ, "(x_1) - (1) = 0")
    testSymbolicCompareInt(x1, x2, COMPARISON_OPS.NE, "(x_1) - (1) /= 0")
    testSymbolicCompareInt(x1, x2, COMPARISON_OPS.GT, "(x_1) - (1) > 0")
    testSymbolicCompareInt(x1, x2, COMPARISON_OPS.LT, "(x_1) - (1) < 0")
    testSymbolicCompareInt(x1, x2, COMPARISON_OPS.GE, "(x_1) - (1) >= 0")
    testSymbolicCompareInt(x1, x2, COMPARISON_OPS.LE, "(x_1) - (1) <= 0")
  }

  @Test
  void testPrintSymbolicOrConstraint() {
    SymbolicInt x1 = new SymbolicInt(1)
    x1.setOp(COMPARISON_OPS.EQ)
    SymbolicInt x2 = new SymbolicInt(2)
    x2.setOp(COMPARISON_OPS.EQ)
    Constraint con = new SymbolicOrConstraint(x1).OR(x2)
    def bytes = new ByteArrayOutputStream()
    def printer = new CVC4Solver.Printer(new HashSet<String>(), 
      new HashMap<String, Long>(), CVC4Solver.CONSTRAINT_TYPE.INT, new PrintStream(bytes))
    printer.print(con)
    assertEquals("(x1*(1) = 0) OR (x2*(1) = 0)", bytes.toString())
  }

  @Test
  void testPrintSymbolicAndConstraint() {
    SymbolicInt x1 = new SymbolicInt(1)
    x1.setOp(COMPARISON_OPS.EQ)
    SymbolicInt x2 = new SymbolicInt(2)
    x2.setOp(COMPARISON_OPS.EQ)
    Constraint con = new SymbolicAndConstraint(x1).AND(x2)
    def bytes = new ByteArrayOutputStream()
    def printer = new CVC4Solver.Printer(new HashSet<String>(), 
      new HashMap<String, Long>(), CVC4Solver.CONSTRAINT_TYPE.INT, new PrintStream(bytes))
    printer.print(con)
    assertEquals("(x1*(1) = 0) AND (x2*(1) = 0)", bytes.toString())
  }

  @Test
  void testPrintSymbolicNotConstraint() {
    SymbolicInt x1 = new SymbolicInt(1)
    x1.setOp(COMPARISON_OPS.EQ)
    Constraint con = new SymbolicNotConstraint(x1)
    def bytes = new ByteArrayOutputStream()
    def printer = new CVC4Solver.Printer(new HashSet<String>(), 
      new HashMap<String, Long>(), CVC4Solver.CONSTRAINT_TYPE.INT, new PrintStream(bytes))
    printer.print(con)
    assertEquals(" NOT (x1*(1) = 0)", bytes.toString())
  }

  @Test
  void testPrintSymbolicTrueConstraint() {
    Constraint con = SymbolicTrueConstraint.instance
    def bytes = new ByteArrayOutputStream()
    def printer = new CVC4Solver.Printer(new HashSet<String>(), 
      new HashMap<String, Long>(), CVC4Solver.CONSTRAINT_TYPE.INT, new PrintStream(bytes))
    printer.print(con)
    assertEquals(" TRUE ", bytes.toString())
  }

  @Test
  void testPrintSymbolicFalseConstraint() {
    Constraint con = SymbolicFalseConstraint.instance
    def bytes = new ByteArrayOutputStream()
    def printer = new CVC4Solver.Printer(new HashSet<String>(), 
      new HashMap<String, Long>(), CVC4Solver.CONSTRAINT_TYPE.INT, new PrintStream(bytes))
    printer.print(con)
    assertEquals(" FALSE ", bytes.toString())
  }

  @Test
  void testPrintSymbolicStringPredicate() {
    SymbolicStringPredicate con = new SymbolicStringPredicate(
      STRING_COMPARISON_OPS.EQ, "a", "b")
    def bytes = new ByteArrayOutputStream()
    def printer = new CVC4Solver.Printer(new HashSet<String>(), 
      new HashMap<String, Long>(), CVC4Solver.CONSTRAINT_TYPE.STR, new PrintStream(bytes))
    printer.print(con)
    assertEquals("((97) - (98) = 0)", bytes.toString())
  }

  @Test
  void testConcatStream() {
    def bytes = new ByteArrayOutputStream()
    def ps = [new PrintStream(bytes)]
    def a = new TreeSet<String>()
    a.addAll(["x1", "x2"])
    solver.concatStreams(ps, a, "from", true)
    assertTrue(bytes.toString().contains("x1"))
    assertTrue(bytes.toString().contains("x1"))
    verify(fileUtil).copyContent("from", ps[0])
  }

  @Test
  void testPrintInput() {
    SymbolicInt x1 = new SymbolicInt(1)
    Value x = new IntValue(2, x1)
    InputElement in1 = new InputElement(1, x)
    SymbolicStringExpression exp = new SymbolicStringExpression(1, new IntValue(2))
    StringValue s = new StringValue("xy", exp)
    InputElement in2 = new InputElement(2, s)
    solver.setInputs([in1, in2])
    def bytes = new ByteArrayOutputStream()
    List<String> result = solver.getSolution(new TreeMap<String, Long>())
    String joined = result.join("")
    assertTrue(joined.contains("2"))
    assertTrue(joined.contains("xy"))
  }

  @Test
  void testPrintFormula() {
    SymbolicInt x1 = new SymbolicInt(1)
    x1.setOp(COMPARISON_OPS.EQ)
    SymbolicInt x2 = new SymbolicInt(2)
    x2.setOp(COMPARISON_OPS.EQ)
    solver.setPathConstraint([(Constraint)x1, (Constraint)x2])
    solver.setPathConstraintIndex(1)
    def bytes = new ByteArrayOutputStream()
    solver.printFormula(new PrintStream(bytes), new TreeMap<String, Long>(), new TreeSet<String>(),
      "extra", CVC4Solver.CONSTRAINT_TYPE.STR)
    println(bytes.toString())
    assertTrue(bytes.toString().contains("ASSERT extra;"))
    assertTrue(bytes.toString().contains("ASSERT x1*(1) = 0;"))
    assertTrue(bytes.toString().contains("CHECKSAT x2*(1) /= 0;"))
  }

  @Test
  void testProcessInputUnsat() {
    config.printFormulaAndSolutions = true
    String result = """unsat
    Whatever!
    """
    InputStream istr = new ByteArrayInputStream(result.getBytes())
    def r = solver.processInputs(new BufferedReader(new InputStreamReader(istr)), 
      new TreeMap<String, Long>())
    assertEquals(null, r)
  }

  @Test
  void testProcessInputSat() {
    config.printFormulaAndSolutions = true
    String result = """sat
x1 : INT = 0;
x2 : INT = 1;"""
    InputStream istr = new ByteArrayInputStream(result.getBytes())
    def soln = new TreeMap<String, Long>()
    def r = solver.processInputs(new BufferedReader(new InputStreamReader(istr)), 
      soln)
    println(soln)
    assertEquals(0L, soln.get("x1"))
    assertEquals(1L, soln.get("x2"))
    assertEquals("(NOT ((x1 = 0 ) AND (x2 = 1 )))", r)  
  }
}