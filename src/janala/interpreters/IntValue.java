/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.interpreters;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/19/12
 * Time: 8:35 AM
 */
public class IntValue extends Value {
    int concrete;

    public IntValue(int i) {
        concrete = i;
    }

    public Value IINC(int increment) {
        return new IntValue(concrete+increment);
    }

    public boolean IFEQ() {
        return (concrete==0);
    }

    public boolean IFNE() {
        return (concrete!=0);
    }

    public boolean IFLT() {
        return (concrete<0);
    }

    public boolean IFGE() {
        return (concrete>=0);
    }

    public boolean IFGT() {
        return (concrete>0);
    }

    public boolean IFLE() {
        return (concrete>=0);
    }

    public boolean IF_ICMPEQ(IntValue i2) {
        return (concrete==i2.concrete);
    }

    public boolean IF_ICMPNE(IntValue i2) {
        return (concrete!=i2.concrete);
    }

    public boolean IF_ICMPLT(IntValue i2) {
        return (concrete<i2.concrete);
    }

    public boolean IF_ICMPGE(IntValue i2) {
        return (concrete>=i2.concrete);
    }

    public boolean IF_ICMPGT(IntValue i2) {
        return (concrete>i2.concrete);
    }

    public boolean IF_ICMPLE(IntValue i2) {
        return (concrete<=i2.concrete);
    }

    public IntValue IADD(IntValue i) {
        return new IntValue(concrete+i.concrete);
    }

    public IntValue ISUB(IntValue i) {
        return new IntValue(concrete-i.concrete);
    }

    public IntValue IMUL(IntValue i) {
        return new IntValue(concrete*i.concrete);
    }

    public IntValue IDIV(IntValue i) {
        return new IntValue(concrete/i.concrete);
    }

    public IntValue IREM(IntValue i) {
        return new IntValue(concrete%i.concrete);
    }

    public IntValue INEG() {
        return new IntValue(-concrete);
    }

    public IntValue ISHL(IntValue i) {
        return new IntValue(concrete<<i.concrete);
    }

    public IntValue ISHR(IntValue i) {
        return new IntValue(concrete>>i.concrete);
    }

    public IntValue IUSHR(IntValue i) {
        return new IntValue(concrete>>>i.concrete);
    }

    public IntValue IAND(IntValue i) {
        return new IntValue(concrete&i.concrete);
    }

    public IntValue IOR(IntValue i) {
        return new IntValue(concrete|i.concrete);
    }

    public IntValue IXOR(IntValue i) {
        return new IntValue(concrete^i.concrete);
    }

    public LongValue I2L() {
        return new LongValue((long)concrete);
    }

    public FloatValue I2F() {
        return new FloatValue((float )concrete);
    }

    public DoubleValue I2D() {
        return new DoubleValue((double)concrete);
    }

    public IntValue I2B() {
        return new IntValue((byte)concrete);
    }

    public IntValue I2C() {
        return new IntValue((char)concrete);
    }

    public IntValue I2S() {
        return new IntValue((short)concrete);
    }

}
