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

package janala.interpreters;

import janala.config.Config;
import janala.solvers.History;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 7/1/12
 * Time: 9:56 AM
 */
public class StaticInvocation {
    public static Value invokeMethod(int iid, String owner, String name, Value[] args, History history) {
        if (owner.equals("java/lang/Integer") && name.equals("valueOf")) {
            IntegerObjectValue ret = new IntegerObjectValue();
            if (args[0] instanceof IntValue) {
                ret.intValue = (IntValue)args[0];
                return ret;
            }
        } else if (owner.equals("java/lang/Long") && name.equals("valueOf")) {
            LongObjectValue ret = new LongObjectValue();
            if (args[0] instanceof LongValue) {
                ret.longValue = (LongValue)args[0];
                return ret;
            }
        } else if (owner.equals("java/sql/Date") && name.equals("valueOf")) {
            SqlDateObjectValue ret = new SqlDateObjectValue();
            if (args[0] instanceof StringValue) {
                ret.longValue = new LongValue((java.sql.Date.valueOf(((StringValue)args[0]).getConcrete()).getTime()));
                return ret;
            }
        } else if (owner.equals("java/sql/Time") && name.equals("valueOf")) {
            SqlDateObjectValue ret = new SqlDateObjectValue();
            if (args[0] instanceof StringValue) {
                ret.longValue = new LongValue((java.sql.Time.valueOf(((StringValue)args[0]).getConcrete()).getTime()));
                return ret;
            }
        } else if (owner.equals("java/sql/Timestamp") && name.equals("valueOf")) {
            SqlDateObjectValue ret = new SqlDateObjectValue();
            if (args[0] instanceof StringValue) {
                ret.longValue = new LongValue((java.sql.Timestamp.valueOf(((StringValue)args[0]).getConcrete()).getTime()));
                return ret;
            }
        } else if (owner.equals("janala/Main") && name.equals("Assume") && args.length==1) {
            if (((IntValue)args[0]).concrete!=0) {
                history.setLastBranchDone();
            }
            return PlaceHolder.instance;
        } else if (owner.equals("janala/Main") && name.equals("ForceTruth") && args.length==1) {
            history.setLastForceTruth();
            return PlaceHolder.instance;
        } else if (owner.equals("janala/Main") && name.equals("MakeSymbolic") && args.length==1) {
            int symbol = args[0].MAKE_SYMBOLIC(history);
            history.addInput(symbol, args[0]);
            return PlaceHolder.instance;
        } else if (owner.equals("janala/Main") && name.equals("BeginScope") && args.length==0) {
            history.addInput(Config.instance.scopeBeginSymbol, null);
            history.beginScope(iid);
            return PlaceHolder.instance;
        } else if (owner.equals("janala/Main") && name.equals("EndScope") && args.length==0) {
            history.addInput(Config.instance.scopeEndSymbol, null);
            history.endScope(iid);
            return PlaceHolder.instance;
        } else if (owner.equals("janala/Main") && name.equals("AbstractEqualsConcrete") && args.length==1) {
            history.abstractData(((IntValue) args[0]).concrete != 0, iid);
            return PlaceHolder.instance;
        } else if (owner.equals("janala/Main") && name.equals("AssumeOrBegin") && args.length==1) {
            Constraint last = history.removeLastBranch();
            boolean res = ((IntValue)args[0]).concrete!=0;
            if (!res && last!=null) last = last.not();
            return new SymbolicOrValue(res,new SymbolicOrConstraint(last));
        } else if (owner.equals("janala/Main") && name.equals("AssumeOr") && args.length==2) {
            Constraint last = history.removeLastBranch();
            SymbolicOrValue b2 = (SymbolicOrValue)args[1];
            SymbolicOrConstraint tmp;
            boolean res = ((IntValue)args[0]).concrete!=0;
            if (!res && last!=null) last = last.not();
            tmp = b2.symbolic.OR(last);
            return new SymbolicOrValue(res || b2.concrete,tmp);
        } else if (owner.equals("janala/Main") && name.equals("AssumeOrEnd") && args.length==1) {
            SymbolicOrValue b = (SymbolicOrValue)args[0];
            boolean res = b.concrete;
            Constraint c;
            if (!res)
                c = b.symbolic.not();
            else
                c = b.symbolic;
            history.checkAndSetBranch(res, c, iid);
            if (b.concrete) {
                history.setLastBranchDone();
            }
            return PlaceHolder.instance;
        } else if (owner.equals("janala/Main") && name.equals("Ignore") && args.length==0) {
            history.setIgnore();
            return PlaceHolder.instance;
        }


        return PlaceHolder.instance;
    }
}
