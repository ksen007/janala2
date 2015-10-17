package janala.instrument;

/** An object to keep track of (classId, methodId, instructionId) tuples during
 instrumentation. */
public class GlobalStateForInstrumentation {
  public static GlobalStateForInstrumentation instance = new GlobalStateForInstrumentation();
  private int iid = 0;
  private int mid = 0;
  private int cid = 0;

  // When one gets the id, she gets the result of mergind all three ids.
  // NOTE
  // Beaware of truncation errors.
  private final static int CBITS = 14; // CID occupies the upper 14 bits
  private final static int MBITS = 8;  // MID occupies the next 8 bits

  /** Increment iid and get the complete id */
  public int incAndGetId() {
    iid++;
    return getId();
  }

  public int getId() {
    return (cid << (32 - CBITS)) + (mid << (32 - CBITS - MBITS)) + iid;
  }

  // Used for testing
  public void reset() {
    iid = 0;
    mid = 0;
    cid = 0;
  }

  public static int getCidMid(int cid, int mid) {
    return cid << MBITS + mid;
  }

  public static int extractCidMid(int id) {
    return (id >> 32 - MBITS - CBITS);
  }

  public int getMid() {
    return mid;
  }

  public void incMid() {
    this.mid++;
    this.iid = 0;
  }

  public int getCid() {
    return cid;
  }

  public void setCid(int cid) {
    this.iid = 0;
    this.mid = 0;
    this.cid = cid;
  }
}
