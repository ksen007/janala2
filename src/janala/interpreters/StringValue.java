/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.interpreters;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/19/12
 * Time: 12:12 PM
 */
public class StringValue extends ObjectValue {
    private String string;
    private SymbolicString symbolic;

    @Override
    public Object getConcrete() {
        return concrete;
    }

    public StringValue(String string, int address) {
        super(100,address);
        this.string = string;
    }

    @Override
    public Value invokeMethod(String name, Value[] args) {
        if (name.equals("equals") && args.length == 1) {
            if (args[0] instanceof StringValue) {
                StringValue other = (StringValue)args[0];
                if (symbolic !=null) {
                    if (string.equals(other.string)) {
                        return new IntValue(1, symbolic.EQ(other.string));
                    } else {
                        return new IntValue(0,symbolic.NE(other.string));
                    }
                } else if (other.symbolic != null) {
                    if (string.equals(other.string)) {
                        return new IntValue(1,other.symbolic.EQ(string));
                    } else {
                        return new IntValue(0,other.symbolic.NE(string));
                    }
                }
            }
        }
        return super.invokeMethod(name, args);
    }

    public void MAKE_SYMBOLIC(int symbol) {
        symbolic = new SymbolicString(symbol);
    }
}
