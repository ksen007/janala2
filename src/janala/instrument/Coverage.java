/*
 * Copyright (c) 2012, NTT Multimedia Communications Laboratories, Inc. and Koushik Sen
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package janala.instrument;

import janala.config.Config;
import janala.utils.MyLogger;

import java.io.*;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */
public class Coverage implements Serializable {
    private HashMap<String,Integer> classNameToCid;
    private TreeMap<Integer, String> cidmidToName;
    private int nBranches;
    private int nCovered;
    private TreeMap<Integer, Integer> covered;
    private TreeMap<Integer, Integer> tmpCovered;
    private boolean isNewClass;

    public static Coverage instance = null;
    private final static Logger logger = MyLogger.getLogger(Coverage.class.getName());
    private String lastMethod;
    private String lastClassName;


    private Coverage() {
        classNameToCid = new HashMap<String,Integer>();
        nBranches = 0;
        nCovered = 0;
        covered = new TreeMap<Integer, Integer>();
        tmpCovered = new TreeMap<Integer, Integer>();
        cidmidToName = new TreeMap<Integer, String>();
    }

    public static void read() {
        if (instance == null) {
            ObjectInputStream inputStream = null;

            try {
                inputStream = new ObjectInputStream(new FileInputStream(Config.instance.coverage));
                Object tmp = inputStream.readObject();
                if (tmp instanceof Coverage) {
                    instance = (Coverage) tmp;
                } else {
                    instance = new Coverage();
                }
            } catch (Exception e) {
                instance = new Coverage();
            } finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (IOException ex) {
                    logger.log(Level.WARNING, "", ex);
                }
            }
        }
    }

    public static void write() {
        ObjectOutputStream outputStream;
        try {
            outputStream = new ObjectOutputStream(new FileOutputStream(Config.instance.coverage));
            instance.tmpCovered.clear();
            outputStream.writeObject(instance);
            outputStream.close();

        } catch (IOException e) {
            logger.log(Level.SEVERE, "", e);
            System.exit(1);
        }

    }


    public int getCid(String cname) {
        int ret;
        lastClassName = cname;
        if (classNameToCid.containsKey(cname)) {
            isNewClass = false;
            return classNameToCid.get(cname);
        } else {
//            System.out.println(cname);
            classNameToCid.put(cname, ret = classNameToCid.size());
            if (cname.equals("catg/CATG"))
                isNewClass = false;
            else
                isNewClass = true;
            return ret;
        }
    }

    public void setCidmidToName(int mid) {
        int cid = classNameToCid.get(lastClassName);
        int cidmid = (cid << GlobalStateForInstrumentation.MBITS) + mid;
        cidmidToName.put(cidmid, lastClassName+"."+lastMethod);
    }

    public void addBranchCount(int iid) {
        if (isNewClass) {
            nBranches += 2;
            covered.put(iid, 0);
            //System.out.println(iid);
        }
    }

    public void visitBranch(int iid, boolean side) {
        if (!tmpCovered.containsKey(iid)) {
            tmpCovered.put(iid, 0);
        }
        tmpCovered.put(iid, tmpCovered.get(iid)|(side?1:2));
    }

    public void commitBranches() {
        for (int key : tmpCovered.keySet()) {
            int value = tmpCovered.get(key);
            if (covered.containsKey(key)) {
                int oldValue = covered.get(key);
                covered.put(key, oldValue | value);
                if ((value & 2) > (oldValue & 2)) {
                    nCovered++;
                    //System.out.println(key + " false ");
                }
                if ((value & 1) > (oldValue & 1)) {
                    nCovered++;
                    //System.out.println(key + " true ");
                }
            }
        }
        printCoverage();
    }

    public void printCoverage() {
        TreeMap<Integer, Integer> methodToTotalBranches = new TreeMap<Integer, Integer>();
        TreeMap<Integer, Integer> methodToCoveredBranches = new TreeMap<Integer, Integer>();
        TreeMap<Integer, Boolean> mcovered = new TreeMap<Integer, Boolean>();
        for (int key : covered.keySet()) {
            //System.out.println(key);
            int cidmid = key >> (32-GlobalStateForInstrumentation.CBITS-GlobalStateForInstrumentation.MBITS);
            if (!methodToTotalBranches.containsKey(cidmid)) {
                methodToTotalBranches.put(cidmid, 0);
                methodToCoveredBranches.put(cidmid, 0);
                mcovered.put(cidmid, false);
            }
            methodToTotalBranches.put(cidmid, methodToTotalBranches.get(cidmid)+2);
            int value = covered.get(key);
            if (value > 0) {
                if ((value & 2) > 0)
                    methodToCoveredBranches.put(cidmid, methodToCoveredBranches.get(cidmid)+1);
                if ((value & 1) > 0)
                    methodToCoveredBranches.put(cidmid, methodToCoveredBranches.get(cidmid)+1);
                mcovered.put(cidmid, true);
            }
        }
        int mtotals = 0;
        int nM = 0;
        for (int key : methodToTotalBranches.keySet()) {
            if (mcovered.get(key)) {
                mtotals+=methodToTotalBranches.get(key);
//                System.out.println("In "+cidmidToName.get(key)+" covered "+methodToCoveredBranches.get(key)+" of "+methodToTotalBranches.get(key)+" branches");
                nM++;
            }
        }
        //System.out.println("Branches covered ="+nCovered);
        //System.out.println("Total branches ="+covered.size());
        System.out.println("Branch coverage with respect to covered classes = "+(100.0*nCovered/nBranches)+"%");
        System.out.println("Branch coverage with respect to covered methods = "+(100.0*nCovered/mtotals)+"%");
        System.out.println("Total branches in covered methods = "+mtotals);
        //System.out.println("Methods covered = "+nM);
        //System.out.println("Total methods = "+counters.size());
    }

    public void setLastMethod(String lastMethod) {
        this.lastMethod = lastMethod;
    }

    public String getLastMethod() {
        return lastMethod;
    }
}
