package janala.logger;

import janala.logger.inst.*;
import janala.config.Config;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class FileLogger extends AbstractLogger {

  private ObjectOutputStream outputStream;

  private class LoggerThread extends Thread {
    @Override
    public void run() {
      try {
        outputStream.close();
        outputStream = new ObjectOutputStream(new FileOutputStream(Config.instance.traceAuxFileName));
        outputStream.writeObject(ClassNames.getInstance());
        outputStream.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public FileLogger() {
    try {
      outputStream = new ObjectOutputStream(new FileOutputStream(Config.instance.traceFileName));
      Runtime.getRuntime().addShutdownHook(new LoggerThread());
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
  }


  @Override
  protected void log(Instruction insn) {
    if (Config.instance.printTrace) System.out.println(insn);
    try {
      outputStream.writeObject(insn);
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
  }
}
