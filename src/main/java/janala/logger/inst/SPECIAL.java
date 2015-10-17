package janala.logger.inst;

/** A special probe instruction added by the instrumentation. 
  * The int value identifies which branch does the instruction correspond to. 
  */
public class SPECIAL extends Instruction {
  public int i;

  public SPECIAL(int i) {
    super(-1, -1);
    this.i = i;
  }

  public void visit(IVisitor visitor) {
    visitor.visitSPECIAL(this);
  }

  @Override
  public String toString() {
    return "SPECIAL i=" + i;
  }
}
