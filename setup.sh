#!/bin/bash

# A convenient library to build the tool.
#
# Required: Gradle.


SCRIPT_DIR=`pwd`
ROOT=$SCRIPT_DIR

echo $ROOT
cd ${ROOT}

echo "Running Gradle to build the library"
gradle jar
cp -f build/libs/janala2-0.2.jar lib/catg-dev.jar

