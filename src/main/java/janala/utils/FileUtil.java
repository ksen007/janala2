package janala.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

public class FileUtil {
  public void moveFile(String src, String dst) {
    File sf1 = new File(src);
    File df1 = new File(dst);
    df1.delete();
    df1 = new File(dst);
    sf1.renameTo(df1);
  }

  public void touch(String src) {
    File file = new File(src);
    if (!file.exists()) {
      try {
        new FileOutputStream(file).close();
      } catch (IOException e) {
        e.printStackTrace();
        System.exit(1);
      }
    }
  }

  public boolean exists(String src) {
    File file = new File(src);
    return file.exists();
  }

  public void remove(String src) {
    File file = new File(src);
    file.delete();
  }

  public void copyContent(String from, PrintStream to) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(from));
    String line = reader.readLine();
    while (line != null) {
      to.println(line);
      line = reader.readLine();
    }
    reader.close();
  }
  
  public void write(String fileName, List<String> content) throws IOException {
    PrintStream out = new PrintStream(new FileOutputStream(fileName));
    for (String e : content) {
      out.println(e);
    }
    out.close();
  }
}
