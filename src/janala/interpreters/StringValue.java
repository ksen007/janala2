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
    public String getConcrete() {
        return string;
    }

    @Override
    public Value invokeMethod(String name, Value[] args) {
        if (name.equals("equals") && args.length == 1) {
            if (args[0] instanceof StringValue) {
                StringValue other = (StringValue)args[0];
                boolean result = string.equals(other.string);
                if (symbolic !=null && other.symbolic !=null) {
                    SymbolicInt ret = symbolic.subtract(other.symbolic);
                    return new IntValue(result?1:0,ret!=null?ret.setop(SymbolicInt.COMPARISON_OPS.EQ):null);
                } else if (symbolic != null) {
                    IntValue ret = new IntValue(result?1:0,symbolic.subtract(StringConstants.instance.get(other.string)).setop(SymbolicInt.COMPARISON_OPS.EQ));
                    return ret;
                } else if (other.symbolic!=null) {
                    SymbolicInt ret = other.symbolic.subtract(StringConstants.instance.get(string));
                    return new IntValue(result?1:0,ret.setop(SymbolicInt.COMPARISON_OPS.EQ));
                } else {
                    return new IntValue(result?1:0,null);
                }
            }
        }
        return super.invokeMethod(name, args);
    }

    public void MAKE_SYMBOLIC(int symbol) {
        symbolic = new SymbolicInt(symbol);
    }
}
