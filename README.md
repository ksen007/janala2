CATG
========

CATG is a concolic unit testing engine for Java programs.  The implementation uses ASM for instrumentation.
ASM instrumentation instruments (see janala.instrument.*) class files at runtime and dumps (see janala.logger.*)
to a file a log of all instructions executed by the program and all values loaded from local stacks and heaps.
A concolic execution engine (see janala.interpreters.*) then takes the log and performs both symbolic and
concrete interpretation of the logged instructions.

You must have java, gradle, cvc4 (http://cvc4.cs.nyu.edu/downloads/), and python 2.7 in your PATH. Two extra jar files are needed to run the tool. Create a `lib` directory in the root directory and download two jar files   

 * asm-all-5.0.4.jar: http://mvnrepository.com/artifact/org.ow2.asm/asm-all/5.0.4
 * automaton-1.11-8.jar: http://mvnrepository.com/artifact/dk.brics.automaton/automaton/1.11-8

place them in the `lib` directory. Then invoke

    gradle build
    
and

    gradle integrationTest
    
To run the the full integration tests, use

    ./setup.sh  # This builds CATG and copy it to lib/
    python testall.py

To run tests and see coverage report, use

    gradle build
    gradle jacocoTestReport

This runs the tests using online concolic execution.  To run tests with offline concolic execution, use

    python --offline testall.py


If you want to generate tests on a Java class file having a main method, you need to use the concolic.py script.  For example, the following command generates test inputs for the class tests.Testme (the java source of this class can be found in [src/integration/java/tests/Testme.java](src/integration/java/tests/Testme.java).

    python concolic.py --coverage 100 tests.Testme
    


### Usage

python janala/concolic.py -h
usage: concolic.py [-h] [--offline] [-v] [-c] [-D D]
                   maxIterations className [arguments [arguments ...]]

positional arguments:
    
    maxIterations   Maximum number of times the program under test can be
                  executed.
    className       Java class to be tested.
    arguments       Arguments passed to the program under test.

optional arguments:

    -h, --help      show this help message and exit
    --offline       Perform concolic testing offline. An intermediate trace file
                  is generated during the execution of the program. offilne
                  mode results in 2X slowdown that non-offline mode
    -v, --verbose   Print commands that are executed.
    -c, --coverage  Compute detailed coverage by rerunning tests.
    -D D            JVM options
