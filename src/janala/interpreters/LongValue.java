/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.interpreters;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/19/12
 * Time: 10:00 AM
 */
public class LongValue extends Value {
    long concrete;

    public LongValue(long concrete) {
        this.concrete = concrete;
    }

    public LongValue LADD(LongValue i) {
        return new LongValue(concrete+i.concrete);
    }

    public LongValue LSUB(LongValue i) {
        return new LongValue(concrete-i.concrete);
    }

    public LongValue LMUL(LongValue i) {
        return new LongValue(concrete*i.concrete);
    }

    public LongValue LDIV(LongValue i) {
        return new LongValue(concrete/i.concrete);
    }

    public LongValue LREM(LongValue i) {
        return new LongValue(concrete%i.concrete);
    }

    public LongValue LNEG() {
        return new LongValue(-concrete);
    }

    public LongValue LSHL(LongValue i) {
        return new LongValue(concrete<<i.concrete);
    }

    public LongValue LSHR(LongValue i) {
        return new LongValue(concrete>>i.concrete);
    }

    public LongValue LUSHR(LongValue i) {
        return new LongValue(concrete>>>i.concrete);
    }

    public LongValue LAND(LongValue i) {
        return new LongValue(concrete&i.concrete);
    }

    public LongValue LOR(LongValue i) {
        return new LongValue(concrete|i.concrete);
    }

    public LongValue LXOR(LongValue i) {
        return new LongValue(concrete^i.concrete);
    }

    public IntValue L2I() {
        return new IntValue((int)concrete);
    }

    public FloatValue L2F() {
        return new FloatValue((float )concrete);
    }

    public DoubleValue L2D() {
        return new DoubleValue((double)concrete);
    }

    public IntValue LCMP(LongValue i2) {
        if (concrete == i2.concrete) {
            return new IntValue(0);
        } else if (i2.concrete > concrete) {
            return new IntValue(1);
        } else {
            return new IntValue(-1);
        }
    }
}
