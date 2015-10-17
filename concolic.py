import shutil
import os
import subprocess
import platform
import sys
import shlex
import argparse

from datetime import datetime

def getArguments ():
    parser = argparse.ArgumentParser()
    parser.add_argument("--offline", help="Perform concolic testing offline.  An intermediate trace file is generated during the execution of the program. offilne mode results in 2X slowdown that non-offline mode", action="store_true")
    parser.add_argument("-v", "--verbose", help="Print commands that are executed.", action="store_true")
    parser.add_argument("-c", "--coverage", help="Compute detailed coverage by rerunning tests.", action="store_true")
    parser.add_argument("-D", help="JVM options", action="append")
    parser.add_argument("maxIterations", help="Maximum number of times the program under test can be executed.", type=int)
    parser.add_argument("className", help="Java class to be tested.")
    parser.add_argument("arguments", nargs='*', help="Arguments passed to the program under test.")
    args = parser.parse_args()
    return args


catg_tmp_dir = "catg_tmp"

def concolic ():
    cmd1 = ("java -Xmx4096M -Xms2048M -Djanala.loggerClass=" + loggerClass
            + " -Djanala.conf=" + catg_home + "catg.conf "
            + jvmOpts + " -javaagent:\""
            + catg_home + "lib/catg-dev.jar\" -cp "
            + classpath+" -ea "+yourpgm+" "+arguments)

    cmd1List = shlex.split(cmd1)
    if verbose:
        print cmd1
    try:
        shutil.rmtree(catg_tmp_dir)
    except: pass
    os.mkdir(catg_tmp_dir)
    os.chdir(catg_tmp_dir)

    print "Now testing "+yourpgm

    i = 1
    while i <= iters:
        try:
            try:
                with open ("isRealInput", "r") as myfile:
                    data=myfile.read().replace('\n', '')
                print data
            except:
                data = "true"
            if data != "false":
                shutil.copy("inputs", "inputs{}".format(i))
            shutil.copy("inputs", "inputs.old")
        except:
            pass
        try:
            shutil.copy("history", "history.old")
        except:
            pass
        dt = datetime.now()

        print "[Input {} at ({}, {}, {}, {}, {})]".format(i, dt.day, dt.hour, dt.minute, dt.second, dt.microsecond)
        sys.stdout.flush()
        subprocess.call(cmd1List, shell=windows)
        if isOffline:
            print "..."
            cmd2 = "java -Xmx4096M -Xms2048M -Djanala.conf="+catg_home+"catg.conf -Djanala.mainClass="+yourpgm+" -Djanala.iteration="+str(i)+" -cp "+classpath+" -ea janala.interpreters.LoadAndExecuteInstructions"
            if verbose:
                print cmd2
            cmd2List = shlex.split(cmd2)
            subprocess.call(cmd2List, shell=windows)
        i = i + 1
        if os.path.isfile("history") or os.path.isfile("backtrackFlag"):
            pass
        elif i == iters:
            with open("../test.log", 'a') as f:
                f.write("{} ({}) passed\n".format(yourpgm, iters))
            return
        else:
            with open("../test.log", 'a') as f:
                f.write("****************** {} ({}) failed!!!\n".format(yourpgm, iters))
            return
    with open("../test.log", 'a') as f:
        f.write("****************** {} ({}) failed!!!\n".format(yourpgm, iters))

def remove(file):
    try:
        os.remove(file)
    except:
        pass


def rerunTests():
    print "Rerunning tests"
    cmd1 = "java -Xmx4096M -Xms2048M -ea -Djanala.conf="+catg_home+"catg.conf "+jvmOpts+" -cp "+catg_home+"lib/emma.jar emmarun -merge yes -raw -sp "+catg_home+"src/ -cp "+ classpath+" "+yourpgm+" "+arguments
    cmd1List = shlex.split(cmd1)
    remove('inputs')
    remove('inputs.bak')
    remove('inputs.old')
    print "[inputs1]"
    if verbose:
        print cmd1
    subprocess.call(cmd1List, shell=windows)
    for filename in os.listdir('.'):
        if filename.startswith('inputs'):
            shutil.copy(filename, "inputs")
            print "["+filename+"]"
            if verbose:
                print cmd1
            subprocess.call(cmd1List, shell=windows)
    cmd2 = "java -cp "+catg_home+"lib/emma.jar emma report -r html -in coverage.es -sp "+catg_home+"src/"
    cmd2List = shlex.split(cmd2)
    subprocess.call(cmd2List, shell=windows)

if platform.system() == "Windows":
    sep = ";"
    windows=True
else:
    sep = ":"
    windows=False

catg_home = os.path.abspath(os.path.dirname(__file__)).replace("\\","/")+"/"

classpath = (catg_home + "build/classes/integration" + sep 
             + catg_home + "lib/asm-all-5.0.4.jar" + sep
             + catg_home+"lib/automaton-1.11-8.jar" + sep
             + catg_home+"lib/catg-dev.jar")
args = getArguments()
iters = args.maxIterations
yourpgm = args.className
isOffline = args.offline
verbose = args.verbose
print args.D
if not args.D == None:
    jvmOpts = "-D"+(" -D".join(args.D))
else:
    jvmOpts = ""
print jvmOpts
if isOffline:
    loggerClass = "janala.logger.FileLogger"
else:
    loggerClass = "janala.logger.DirectConcolicExecution"
arguments = ' '.join(args.arguments)

concolic()
if args.coverage:
    rerunTests()
    print "\n\n*********************************************************************************************"
    print "To see detailed coverage information open the file catg_tmp/coverage/index.html in a browser."
    print "*********************************************************************************************\n"
