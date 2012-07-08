package catg;/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

import janala.Main;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 7/1/12
 * Time: 12:09 PM
 */
public class CATG {
    public static int readInt(int x) {
        int y = Main.readInt(x);
        Main.MakeSymbolic(y);
        Main.Assume(y <= Integer.MAX_VALUE?1:0);
        Main.Assume(y >= Integer.MIN_VALUE?1:0);
        return y;
    }

    public static long readLong(long x) {
        long y = Main.readLong(x);
        Main.MakeSymbolic(y);
        Main.Assume(y <= Long.MAX_VALUE?1:0);
        Main.Assume(y >= Long.MIN_VALUE?1:0);
        return y;
    }

    public static char readChar(char x) {
        char y = Main.readChar(x);
        Main.MakeSymbolic(y);
        Main.Assume(y <= Character.MAX_VALUE?1:0);
        Main.Assume(y >= Character.MIN_VALUE?1:0);
        return y;
    }

    public static byte readByte(byte x) {
        byte y = Main.readByte(x);
        Main.MakeSymbolic(y);
        Main.Assume(y <= Byte.MAX_VALUE?1:0);
        Main.Assume(y >= Byte.MIN_VALUE?1:0);
        return y;
    }

    public static short readShort(short x) {
        short y = Main.readShort(x);
        Main.MakeSymbolic(y);
        Main.Assume(y <= Short.MAX_VALUE?1:0);
        Main.Assume(y >= Short.MIN_VALUE?1:0);
        return y;
    }

    public static boolean readBool(boolean x) {
        boolean y = Main.readBool(x);
        Main.MakeSymbolic(y);
        return y;
    }

    public static String readString(String x) {
        String y = Main.readString(x);
        Main.MakeSymbolic(y);
        return y;
    }
}
