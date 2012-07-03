/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala;

import janala.config.Config;

import java.io.*;
import java.util.ArrayList;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/21/12
 * Time: 4:50 PM
 */
public class Main {
    public static void Assume(boolean b) {
        if (!b) {
            System.out.println("Assumption failed!");
            System.exit(0);
        }
    }

    public static void MakeSymbolic(int x) {    }
    public static void MakeSymbolic(long x) {    }
    public static void MakeSymbolic(char x) {    }
    public static void MakeSymbolic(byte x) {    }
    public static void MakeSymbolic(short x) {    }
    public static void MakeSymbolic(boolean x) {    }
    public static void MakeSymbolic(String x) {    }

    static public int readInt(int x) {
        if (index < inputs.size()) {
            String input = inputs.get(index++);
            //System.out.println(input);
            return Integer.parseInt(input);
        } else {
            //System.out.println(x);
            return x;
        }
    }

    static public long readLong(long x) {
        if (index < inputs.size()) {
            String input = inputs.get(index++);
            //System.out.println(input);
            return Long.parseLong(input);
        } else {
            //System.out.println(x);
            return x;
        }
    }

    static public char readChar(char x) {
        if (index < inputs.size()) {
            String input = inputs.get(index++);
            //System.out.println(input);
            return input.charAt(0);
        } else {
            //System.out.println(x);
            return x;
        }
    }

    static public short readShort(short x) {
        if (index < inputs.size()) {
            String input = inputs.get(index++);
            //System.out.println(input);
            return Short.parseShort(input);
        } else {
            //System.out.println(x);
            return x;
        }
    }

    static public byte readByte(byte x) {
        if (index < inputs.size()) {
            String input = inputs.get(index++);
            //System.out.println(input);
            return Byte.parseByte(input);
        } else {
            //System.out.println(x);
            return x;
        }
    }

    static public boolean readBool(boolean x) {
        if (index < inputs.size()) {
            String input = inputs.get(index++);
            boolean ret = Integer.parseInt(input)!=0;
            //System.out.println(ret);
            return ret;
        } else {
            //System.out.println(x);
            return x;
        }
    }

    static public String readString(String x) {
        if (index < inputs.size()) {
            String input = inputs.get(index++);
            //System.out.println(input);
            return new String(input);
        } else {
            //System.out.println(x);
            return new String(x);
        }
    }

    private static ArrayList<String> inputs;
    private static int index;

    static {
        inputs = new ArrayList<String>();
        index = 0;
        DataInputStream in = null;
        try{
            FileInputStream fstream = new FileInputStream(Config.inputs);
            in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            while ((strLine = br.readLine()) != null)   {
                inputs.add(strLine);
            }
            in.close();
        } catch (Exception e) {
            //System.err.println("Error: " + e.getMessage());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
            }
        }
    }


}
