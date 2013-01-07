package janala.interpreters;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 1/6/13
 * Time: 7:19 PM
 */
public class SymOrInt {
    public String sym;
    public long constant;

    public SymOrInt(String sym) {
        this.sym = sym;
    }

    public SymOrInt(long constant) {
        this.constant = constant;
    }

    @Override
    public String toString() {
        if (sym != null) return sym;
        else return ""+constant;
    }
}
