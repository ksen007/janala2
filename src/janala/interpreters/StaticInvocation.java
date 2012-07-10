/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.interpreters;

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
        } else if (owner.equals("janala/Main") && name.equals("Assume") && args.length==1) {
            if (((IntValue)args[0]).concrete!=0) {
                history.setLastBranchDone();
            }
            return PlaceHolder.instance;
        } else if (owner.equals("janala/Main") && name.equals("AssumeOrBegin") && args.length==1) {
            Constraint last = history.removeLastBranch();
            boolean res = ((IntValue)args[0]).concrete!=0;
            return new SymbolicOrValue(res,new SymbolicOrConstraint(res?last:last.not()));
        } else if (owner.equals("janala/Main") && name.equals("AssumeOr") && args.length==2) {
            Constraint last = history.removeLastBranch();
            SymbolicOrValue b2 = (SymbolicOrValue)args[1];
            SymbolicOrConstraint tmp;
            boolean res = ((IntValue)args[0]).concrete!=0;
            tmp = b2.symbolic.OR(res?last:last.not());
            return new SymbolicOrValue(res || b2.concrete,tmp);
        } else if (owner.equals("janala/Main") && name.equals("AssumeOrEnd") && args.length==1) {
            SymbolicOrValue b = (SymbolicOrValue)args[0];
            boolean res = b.concrete;
            Constraint c;
            if (!res)
                c = b.symbolic.not();
            else
                c = b.symbolic;
            history.checkAndSetBranch(new ConstraintAndResult(c,res),iid);
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
