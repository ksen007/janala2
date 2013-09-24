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

/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.config;

import janala.solvers.Solver;
import janala.solvers.Strategy;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    public static final String mainClass = System.getProperty("janala.mainClass",null);
    public static final int iteration = Integer.getInteger("janala.iteration",0);
    public static final String propFile = System.getProperty("janala.conf","catg.conf");
    public static final Config instance = new Config();



    public boolean isTest;
    public boolean verbose;
    public boolean printTrace;
    public boolean printConstraints;
    public String analysisClass;
    public String traceFileName;
    public String traceAuxFileName;
    public String history;
    public String inputs;
    public String yicesCommand;
    public String formulaFile;
    public String testLog;
    public String cvc3Command;
    public String[] excludeList;
    public String[] includeList;
    private String solver;
    private String strategy;
    public int maxStringLength;
    public int pathId;
    public boolean printFormulaAndSolutions;

    public Config() {
        try {
            Properties properties = new Properties();
    		properties.load(new FileInputStream(propFile));
            
            isTest = properties.getProperty("catg.isInternalTestMode","false").equals("true");
            verbose = properties.getProperty("catg.isVerbose","false").equals("true");
            printTrace = properties.getProperty("catg.isPrintTrace","false").equals("true");
            printConstraints = properties.getProperty("catg.isPrintConstraints","false").equals("true");
            printFormulaAndSolutions = properties.getProperty("catg.isPrintFormulaAndSolutions","false").equals("true");
            traceFileName = properties.getProperty("catg.traceFile","trace");
            traceAuxFileName = properties.getProperty("catg.auxTraceFile","trace.aux");
            history = properties.getProperty("catg.historyFile","history");
            inputs = properties.getProperty("catg.inputsFile","inputs");
            yicesCommand = properties.getProperty("catg.yicesCommand", "yices");
            formulaFile = properties.getProperty("catg.formulaFile", "formula");
            testLog = properties.getProperty("catg.testLogFile", "test.log");
            cvc3Command = properties.getProperty("catg.cvc3Command", "cvc3");
            analysisClass = properties.getProperty("catg.analysisClass", "janala.logger.DJVM").replace('.','/');
            solver = properties.getProperty("catg.solverClass", "janala.solvers.YicesSolver2");
            strategy = properties.getProperty("catg.strategyClass", "janala.solvers.DFSStrategy");
            excludeList = properties.getProperty("catg.excludeList","").split(",");
            includeList = properties.getProperty("catg.includeList","catg.CATG").split(",");
            maxStringLength = Integer.parseInt(properties.getProperty("catg.maxStringLength","30"));
            pathId = Integer.parseInt(properties.getProperty("catg.pathId","1"));
    	} catch (IOException ex) {
    		ex.printStackTrace();
        }
    }


    public Solver getSolver() {
        try {
            Class solverClass = Class.forName(solver);
            Solver ret = (Solver)solverClass.newInstance();
            return ret;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (InstantiationException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    public Strategy getStrategy() {
        try {
            Class solverClass = Class.forName(strategy);
            Strategy ret = (Strategy)solverClass.newInstance();
            return ret;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (InstantiationException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }
}
