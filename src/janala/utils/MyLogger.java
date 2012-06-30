/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.utils;

import java.util.logging.ConsoleHandler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/29/12
 * Time: 1:58 PM
 */
public class MyLogger {
    private static ConsoleHandler handler = new ConsoleHandler();

    static {
        handler.setFormatter(new MyFormatter());
    }
    
    public static Logger getLogger(String name) {
        Logger ret = Logger.getLogger(name);
        ret.setUseParentHandlers(false);
        ret.addHandler(handler);
        return ret;
    }
}

class MyFormatter extends SimpleFormatter {
    @Override
    public String format(LogRecord logRecord) {
        return logRecord.getLevel()+": "+formatMessage(logRecord)+"\n";
    }
}