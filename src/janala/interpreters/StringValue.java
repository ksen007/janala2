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
    private SymbolicInt symbolic;

    public StringValue(String string, int address) {
        super(100,address);
        this.string = string;
    }

    @Override
    public Object getConcrete() {
        return string;
    }

    @Override
    public Value invokeMethod(String name, Value[] args) {
        if (name.equals("equals") && args.length == 1) {
            if (args[0] instanceof StringValue) {
                StringValue other = (StringValue)args[0];
                boolean result = string.equals(other.string);
                if (symbolic !=null && other.symbolic !=null) {
                    return new IntValue(result?1:0,symbolic.subtract(other.symbolic).setop(result? SymbolicInt.COMPARISON_OPS.NE: SymbolicInt.COMPARISON_OPS.EQ));
                } else if (symbolic != null) {
                    return new IntValue(result?1:0,symbolic.subtract(StringConstants.instance.get(other.string)).setop(result? SymbolicInt.COMPARISON_OPS.NE: SymbolicInt.COMPARISON_OPS.EQ));
                } else {
                    return new IntValue(result?1:0,other.symbolic.subtract(StringConstants.instance.get(string)).setop(result? SymbolicInt.COMPARISON_OPS.NE: SymbolicInt.COMPARISON_OPS.EQ));
                }
            }
        }
        return super.invokeMethod(name, args);
    }

    public void MAKE_SYMBOLIC(int symbol) {
        symbolic = new SymbolicInt(symbol);
    }
}
