/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.interpreters;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/19/12
 * Time: 10:01 AM
 */
public class FloatValue extends Value{
    float concrete;

    public FloatValue(float concrete) {
        this.concrete = concrete;
    }

    public FloatValue FADD(FloatValue d) {
        return new FloatValue(concrete+d.concrete);
    }

    public FloatValue FSUB(FloatValue d) {
        return new FloatValue(concrete-d.concrete);
    }

    public FloatValue FMUL(FloatValue d) {
        return new FloatValue(concrete*d.concrete);
    }

    public FloatValue FDIV(FloatValue d) {
        return new FloatValue(concrete/d.concrete);
    }

    public FloatValue FREM(FloatValue d) {
        return new FloatValue(concrete%d.concrete);
    }

    public FloatValue FNEG() {
        return new FloatValue(-concrete);
    }

    public IntValue F2I() {
        return new IntValue((int)concrete);
    }

    public LongValue F2L() {
        return new LongValue((long)concrete);
    }

    public DoubleValue F2D() {
        return new DoubleValue((double)concrete);
    }

    public IntValue FCMPL(FloatValue d) {
        if (Float.isNaN(concrete) || Float.isNaN(d.concrete)) {
            return new IntValue(-1);
        } else if (concrete == d.concrete) {
            return new IntValue(0);
        } else if (d.concrete > concrete) {
            return new IntValue(1);
        } else {
            return new IntValue(-1);
        }

    }

    public IntValue FCMPG(FloatValue d) {
        if (Float.isNaN(concrete) || Float.isNaN(d.concrete)) {
            return new IntValue(1);
        } else if (concrete == d.concrete) {
            return new IntValue(0);
        } else if (d.concrete > concrete) {
            return new IntValue(1);
        } else {
            return new IntValue(-1);
        }
    }
}
