import shutil
import os
import subprocess
import platform
import sys
import shlex
import argparse

def getArguments ():
    parser = argparse.ArgumentParser()
    parser.add_argument("--offline", help="Perform concolic testing offline.  An intermediate trace file is generated during the execution of the program. offilne mode results in 2X slowdown that non-offline mode", action="store_true")
    parser.add_argument("-v", "--verbose", help="Print commands that are executed.", action="store_true")
    parser.add_argument("maxIterations", help="Maximum number of times the program under test can be executed.", type=int)
    parser.add_argument("className", help="Java class to be tested.")
    parser.add_argument("arguments", nargs='*', help="Arguments passed to the program under test.")
    args = parser.parse_args()
    return args



def concolic ():
    if platform.system() == "Windows":
        sep = ";"
        windows=True
    else:
        sep = ":"
        windows=False
    catg_home = os.path.abspath(os.path.dirname(__file__)).replace("\\","/")+"/"
    classpath = catg_home+"out/production/tests"+sep+catg_home+"out/production/janala"+sep+catg_home+"lib/asm-all-3.3.1.jar"+sep+catg_home+"lib/trove-3.0.3.jar"+sep+catg_home+"lib/automaton.jar"+sep+catg_home+"lib/iagent.jar"
    args = getArguments()
    iters = args.maxIterations
    yourpgm = args.className
    isOffline = args.offline
    verbose = args.verbose
    if isOffline:
        loggerClass = "janala.logger.FileLogger"
    else:
        loggerClass = "janala.logger.DirectConcolicExecution"
    arguments = ' '.join(args.arguments)
    cmd1 = "java -Djanala.loggerClass="+loggerClass+" -Djanala.conf="+catg_home+"catg.conf -javaagent:\""+catg_home+"lib/iagent.jar\" -cp "+ classpath+" -ea "+yourpgm+" "+arguments

    cmd1List = shlex.split(cmd1)
    if verbose:
        print cmd1
    catg_tmp_dir = "catg_tmp"
    try:
        shutil.rmtree(catg_tmp_dir)
    except: pass
    os.mkdir(catg_tmp_dir)
    os.chdir(catg_tmp_dir)

    print "Now testing "+yourpgm

    i = 1
    while i <= iters:
        try: 
            shutil.copy("inputs", "inputs{}".format(i))
            shutil.copy("inputs", "inputs.old")
        except:
            pass
        try: 
            shutil.copy("history", "history.old")
        except:
            pass
        print "[Input {}]".format(i)
        subprocess.call(cmd1List, shell=windows)
        if isOffline:
            print "..."
            cmd2 = "java -Djanala.conf="+catg_home+"catg.conf -Djanala.mainClass="+yourpgm+" -Djanala.iteration="+str(i)+" -cp "+classpath+" -ea janala.interpreters.LoadAndExecuteInstructions"
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
            sys.exit()
        else:
            with open("../test.log", 'a') as f:
                f.write("****************** {} ({}) failed!!!\n".format(yourpgm, iters))
            sys.exit()
    with open("../test.log", 'a') as f:
        f.write("****************** {} ({}) failed!!!\n".format(yourpgm, iters))

concolic()

