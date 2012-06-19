/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.interpreters;

import java.io.PrintStream;
import java.util.Collection;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/19/12
 * Time: 8:28 AM
 */
public interface Symbolic {
        Symbolic negate();

        Symbolic add(long l);

        Symbolic add(Symbolic l);

        Symbolic subtractFrom(long l);

        Symbolic subtract(long l);

        Symbolic subtract(Symbolic l);

        Symbolic multiply(long l);

        void setop(String op);

        void not();

        void print(PrintStream out);

        Collection<Integer> getInputs();
}
