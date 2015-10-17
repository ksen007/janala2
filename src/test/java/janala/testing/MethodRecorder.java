package janala.testing;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.util.Printer;
import org.objectweb.asm.util.Textifier;
import org.objectweb.asm.util.TraceMethodVisitor;

public class MethodRecorder {
  private final Printer printer;
  private final MethodVisitor visitor;

  public MethodRecorder() {
    printer = new Textifier();
    visitor = new TraceMethodVisitor(printer);
  }

  public Printer getPrinter() {
    return printer;
  }

  public MethodVisitor getVisitor() {
    return visitor;
  }

  @Override
    public boolean equals(Object obj) {
    if (!(obj instanceof MethodRecorder)) {
      return false;
    }
    MethodRecorder that = (MethodRecorder) obj;
    return printer.getText().equals(that.printer.getText());
  }

  @Override
    public int hashCode() {
    return printer.getText().hashCode();
  }

  @Override
    public String toString() {
    StringWriter buffer = new StringWriter();
    PrintWriter writer = new PrintWriter(buffer);
    printer.print(writer);
    writer.flush();
    return buffer.toString();
  }
}
