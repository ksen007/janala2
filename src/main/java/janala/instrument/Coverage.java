package janala.instrument;

import janala.utils.MyLogger;

import java.io.*;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Coverage implements Serializable {
  private static final long serialVersionUID = 1L;
  
  private final HashMap<String, Integer> classNameToCid;
  private final TreeMap<Integer, String> cidmidToName;
  private int nBranches;
  private int nCovered;
  private final TreeMap<Integer, Integer> covered;
  private final TreeMap<Integer, Integer> tmpCovered; // transient
  private boolean isNewClass;

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }
    if (o == this) {
      return true;
    }
    
    if (o instanceof Coverage) {
      Coverage other = (Coverage)o;
      return classNameToCid.equals(other.classNameToCid) && 
          cidmidToName.equals(other.cidmidToName) && 
          covered.equals(other.covered) &&
          (nBranches == other.nBranches) &&
          (nCovered == other.nCovered);
    } else {
      return false;
    }
  }
  
  @Override
  public String toString() {
    return "nBranches=" + nBranches + ", nCovered=" + nCovered + ", classNameToCid=" +
        classNameToCid.toString() + ", cidmidToName=" + cidmidToName.toString() + 
        ", covered=" + covered.toString() + ", tmpCovered=" + tmpCovered.toString();
  }
  
  private String lastMethod;
  private String lastClassName;
   
  public static Coverage instance = null;
  
  private static final Logger logger = MyLogger.getLogger(Coverage.class.getName());
  
  
  public Coverage() {
    classNameToCid = new HashMap<String, Integer>();
    nBranches = 0;
    nCovered = 0;
    covered = new TreeMap<Integer, Integer>();
    tmpCovered = new TreeMap<Integer, Integer>();
    cidmidToName = new TreeMap<Integer, String>();
  }

  public static Coverage get() {
    if (instance == null) {
      instance = new Coverage();
    }
    return instance;
  }
  
  /** Parse a coverage object from an input stream */
  public static Coverage parse(InputStream is) {
    ObjectInputStream inputStream = null;
    try {
      inputStream = new ObjectInputStream(is);
      Object tmp = inputStream.readObject();
      inputStream.close();
      if (tmp instanceof Coverage) {
        return (Coverage) tmp;
      } else {
        return new Coverage();
      }
    } catch (Exception e) {
      if (inputStream != null) {
        try {
          inputStream.close();
        } catch (IOException unused) {
        }
      }
      return new Coverage();
    }
  }

  public static void read(String fileName) {
    try {
      instance = parse(new FileInputStream(fileName));
    } catch (Exception e) {
      instance = new Coverage();
    }
  }
  
  public void write(OutputStream os) throws IOException {
    ObjectOutputStream outputStream = new ObjectOutputStream(os);
    this.tmpCovered.clear();
    outputStream.writeObject(this);
    outputStream.close();
  }

  public void write(String outputFile) {
    try {
      if (outputFile != null) {
        write(new FileOutputStream(outputFile));
      }
    } catch (Exception e) {
      logger.log(Level.SEVERE, "", e);
      throw new RuntimeException("Error happened while writing coverage");
    }
     
  }

  public int getCid(String cname) {
    int ret = -1; // invalid
    lastClassName = cname;
    if (classNameToCid.containsKey(cname)) {
      isNewClass = false;
      return classNameToCid.get(cname);
    } else {
      classNameToCid.put(cname, ret = classNameToCid.size());
      if (cname.equals("catg/CATG")) {
        isNewClass = false;
      } else {
        isNewClass = true;
      }
      return ret;
    }
  }

  public void setCidmidToName(int mid) {
    int cid = classNameToCid.get(lastClassName);
    int cidmid = GlobalStateForInstrumentation.getCidMid(cid, mid);
    cidmidToName.put(cidmid, lastClassName + "." + lastMethod);
  }

  public void addBranchCount(int iid) {
    if (isNewClass) {
      nBranches += 2;
      covered.put(iid, 0);
    }
  }

  public void visitBranch(int iid, boolean side) {
    if (!tmpCovered.containsKey(iid)) {
      tmpCovered.put(iid, 0);
    }
    tmpCovered.put(iid, tmpCovered.get(iid) | (side ? 1 : 2));
  }

  public void commitBranches(boolean print) {
    for (int key : tmpCovered.keySet()) {
      int value = tmpCovered.get(key);
      if (covered.containsKey(key)) {
        int oldValue = covered.get(key);
        covered.put(key, oldValue | value);
        if ((value & 2) > (oldValue & 2)) {
          nCovered++;
        }
        if ((value & 1) > (oldValue & 1)) {
          nCovered++;
        }
      }
    }
    if (print) {
      printCoverage(System.out);
    }
  }

  public void printCoverage(PrintStream out) {
    Map<Integer, Integer> methodToTotalBranches = new TreeMap<Integer, Integer>();
    Map<Integer, Integer> methodToCoveredBranches = new TreeMap<Integer, Integer>();
    Map<Integer, Boolean> mcovered = new TreeMap<Integer, Boolean>();
    for (int key : covered.keySet()) {
      int cidmid = GlobalStateForInstrumentation.extractCidMid(key);
      if (!methodToTotalBranches.containsKey(cidmid)) {
        methodToTotalBranches.put(cidmid, 0);
        methodToCoveredBranches.put(cidmid, 0);
        mcovered.put(cidmid, false);
      }
      methodToTotalBranches.put(cidmid, methodToTotalBranches.get(cidmid) + 2);
      int value = covered.get(key);
      if (value > 0) {
        if ((value & 2) > 0) {
          methodToCoveredBranches.put(cidmid, methodToCoveredBranches.get(cidmid) + 1);
        }
        if ((value & 1) > 0) {
          methodToCoveredBranches.put(cidmid, methodToCoveredBranches.get(cidmid) + 1);
        }
        mcovered.put(cidmid, true);
      }
    }
    int mtotals = 0;
    for (int key : methodToTotalBranches.keySet()) {
      if (mcovered.get(key)) {
        mtotals += methodToTotalBranches.get(key);
      }
    }
    out.println(
        "Branch coverage with respect to covered classes = "
            + (100.0 * nCovered / nBranches)
            + "%");
    out.println(
        "Branch coverage with respect to covered methods = " + (100.0 * nCovered / mtotals) + "%");
    out.println("Total branches in covered methods = " + mtotals);
  }

  public void setLastMethod(String lastMethod) {
    this.lastMethod = lastMethod;
  }

  public String getLastMethod() {
    return lastMethod;
  }
}
