package janala.interpreters

import org.junit.Test
import static org.junit.Assert.assertEquals
import java.util.Map
import groovy.transform.CompileStatic

@CompileStatic
public class InterpreterTest {
    @Test
    void testClassTemplate() {
        def ct = new ClassTemplate(TestClass.class)
        assertEquals(2, ct.nFields())
    }

}