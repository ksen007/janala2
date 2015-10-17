package janala.utils;

import janala.config.Config;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;

public class Inputs {
  private int index;
  private List<String> inputs;
  
  public Inputs(String filename) {
    DataInputStream in = null;
    index = 0;
    try {
      FileInputStream fstream = new FileInputStream(filename);
      in = new DataInputStream(fstream);
      BufferedReader br = new BufferedReader(new InputStreamReader(in));
      inputs = new ArrayList<String>();
      String strLine;
      while ((strLine = br.readLine()) != null) {
        inputs.add(strLine);
      }
      in.close();
    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
    } finally {
      try {
        if (in != null) {
          in.close();
        }
      } catch (IOException ex) {
      }
    }
  }
  
  public Inputs(List<String> solution) {
    inputs = solution;
    index = 0;
  }
  
  public String read() {
    String ret = inputs.get(index);
    index++;
    return ret;
  }
  
  public void next() {
    index++;
  }
  
  public boolean isInputAvailable() {
    return (inputs != null) && (index < inputs.size());
  }
  
  public boolean isBeginScope() {
    return inputs.get(index).equals(Config.instance.scopeBeginMarker);
  }
  
  public boolean isEndScope() {
    return inputs.get(index).equals(Config.instance.scopeEndMarker);
  }
}
