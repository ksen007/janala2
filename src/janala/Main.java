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
    public static void MakeSymbolic(int x) {    }
    public static void MakeSymbolic(long x) {    }
    public static void MakeSymbolic(char x) {    }
    public static void MakeSymbolic(byte x) {    }
    public static void MakeSymbolic(short x) {    }
    public static void MakeSymbolic(boolean x) {    }
    public static void MakeSymbolic(Object x) {    }


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

    static public int readInt(int x) {
        if (index < inputs.size()) {
            String input = inputs.get(index++);
            System.out.println(input);
            return Integer.parseInt(input);
        } else {
            System.out.println(x);
            return x;
        }
    }

    static public long readLong(long x) {
        if (index < inputs.size()) {
            String input = inputs.get(index++);
            System.out.println(input);
            return Long.parseLong(input);
        } else {
            System.out.println(x);
            return x;
        }
    }

    static public char readChar(char x) {
        if (index < inputs.size()) {
            String input = inputs.get(index++);
            System.out.println(input);
            return input.charAt(0);
        } else {
            System.out.println(x);
            return x;
        }
    }

    static public short readShort(short x) {
        if (index < inputs.size()) {
            String input = inputs.get(index++);
            System.out.println(input);
            return Short.parseShort(input);
        } else {
            System.out.println(x);
            return x;
        }
    }

    static public byte readByte(byte x) {
        if (index < inputs.size()) {
            String input = inputs.get(index++);
            System.out.println(input);
            return Byte.parseByte(input);
        } else {
            System.out.println(x);
            return x;
        }
    }

    static public float readFloat(float x) {
        if (index < inputs.size()) {
            String input = inputs.get(index++);
            System.out.println(input);
            return Float.parseFloat(input);
        } else {
            System.out.println(x);
            return x;
        }
    }

    static public double readDouble(double x) {
        if (index < inputs.size()) {
            String input = inputs.get(index++);
            System.out.println(input);
            return Double.parseDouble(input);
        } else {
            System.out.println(x);
            return x;
        }
    }

    static public String readString(String x) {
        if (index < inputs.size()) {
            String input = inputs.get(index++);
            System.out.println(input);
            return input;
        } else {
            System.out.println(x);
            return x;
        }
    }

    static public int readObject(int x) {
        if (index < inputs.size()) {
            String input = inputs.get(index++);
            System.out.println(input);
            return Integer.parseInt(input);
        } else {
            System.out.println(x);
            return x;
        }
    }
}
