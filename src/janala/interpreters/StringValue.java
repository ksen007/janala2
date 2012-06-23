/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.interpreters;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/19/12
 * Time: 12:12 PM
 */
public class StringValue extends Value {
    private String concrete;

    @Override
    public Object getConcrete() {
        return concrete;
    }

    public StringValue(String concrete) {
        this.concrete = concrete;
    }
}
