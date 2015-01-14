# Copyright (c) 2014, NTT Multimedia Communications Laboratories, Inc. and Koushik Sen
#
# All rights reserved.
#
# Redistribution and use in source and binary forms, with or without
# modification, are permitted provided that the following conditions are
# met:
#
# 1. Redistributions of source code must retain the above copyright
# notice, this list of conditions and the following disclaimer.
#
# 2. Redistributions in binary form must reproduce the above copyright
# notice, this list of conditions and the following disclaimer in the
# documentation and/or other materials provided with the distribution.
#
# THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
# "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
# LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
# A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
# HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
# SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
# LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
# DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
# THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
# (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
# OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
import argparse
import os
import subprocess
import shlex
import platform

def getArguments ():
    parser = argparse.ArgumentParser()
    parser.add_argument("--offline", help="Perform concolic testing offline.  An intermediate trace file is generated during the execution of the program. offilne mode results in 2X slowdown that non-offline mode", action="store_true")
    parser.add_argument("-v", "--verbose", help="Print commands that are executed.", action="store_true")
    args = parser.parse_args()
    return args


args = getArguments()
try:
    os.remove("test.log")
except: pass
    
if args.offline:
    argument = "--offline"
else:
    argument = ""

if args.verbose:
    argument = argument + " --verbose"


tests = ["5 tests.test_q1_q4_min",
    "9 tests.test_q1_q4",
    "3 tests.test_q0_q4",
    "3 tests.BoolFlow",
    "2 tests.BoolFlow2",
    "3 tests.BoolTest",
    "5 tests.BoolTest2",
    "5 tests.BoolTest3",
    "3 tests.Array1",
    "3 tests.Array2",
    "3 tests.Array3",
    "2 tests.Array4",
    "3 tests.StringEquals",
    "4 tests.StringTest2",
    "4 tests.StringTest3",
    "4 tests.StringTest4",
    "3 tests.StringTest5",
    "4 tests.StringTest6",
    "4 tests.StringTest7",
    "4 tests.StringTest8",
    "4 tests.StringTest9",
    "5 tests.StringSubStringTest2",
    "6 tests.StringComplexTest1",
    "4 tests.StringComplexTest3",
    "3 tests.StringRegexTest1",
    "3 tests.StringRegexTest2",
    "3 tests.StringRegexTest3",
    "5 tests.StringLengthTest1",
    "3 tests.StringLengthTest2",
    "3 tests.StringLengthTest3",
    "3 tests.StringMapTest",
    "4 tests.LongTest",
    "7 tests.LongTest3",
    "4 tests.TimestampTest",
    "4 tests.Testme",
    "4 tests.TestmeInteger",
    "4 tests.TestmeLong",
    "5 tests.NonLinear",
    "5 tests.BoolTest2",
    "8 tests.SwitchTest",
    "3 tests.SwitchTest2",
    "5 tests.Linear",
    "3 tests.SimpleDBTest",
    "17 tests.DBUpdateTest",
    "17 tests.DBSelectTest",
    "17 tests.DBDeleteTest",
    "17 tests.DBDateSelectTest",
    "6 tests.DBInsertTest",
    "5 tests.OrAssumptionTest",
    "4 tests.OrAssumptionTest2",
    "5 tests.AssertIfPossibleTest1",
    "10 tests.AssertIfPossibleTest2",
    "79 tests.ManyColumnsOrRecords",
    "7 tests.AbstractionTest1",
    "14 tests.AbstractionTest2",
    "14 tests.SortedListInsert2",
    "11 tests.SortedListInsert3",
    "7 tests.DataAnnotation6",
    "5 tests.DataAnnotation7",
    "100 tests.ManyColumnsRecords2",
    "29 tests.QSort",
    "29 tests.QSortLong",
    "25 tests.InsertionSort",
    "121 tests.MergeSortJDK15",
    "76 tests.BinaryTreeSearch",
    "13 tests.HeapInsertJDK15",
    "25 tests.QuickSortJDK15",
    "217 tests.RedBlackTreeSearch",
    "121 tests.SortedListInsert",
    "206 tests.BellmanFord",
    "157 tests.Dijkstra",
    "100 tests.BookStoreNoSQL"]


if platform.system() == "Windows":
    windows=True
else:
    windows=False

for test in tests:
    subprocess.call(shlex.split("python concolic.py "+argument+" "+test), shell=windows)

f = open('test.log', 'r')
print f.read()
f.close()

