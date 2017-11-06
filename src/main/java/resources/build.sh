#!/bin/sh

#
# Wojciech Golab, 2016
#
set -ex

#JAVA_CC=/usr/lib/jvm/java-1.8.0/bin/javac
JAVA_CC=javac
#THRIFT_CC=/opt/bin/thrift
THRIFT_CC=thrift

echo --- Cleaning
rm -f *.class
rm -fr gen-java

echo --- Compiling Thrift IDL
$THRIFT_CC --version
$THRIFT_CC --gen java a1.thrift

echo --- Compiling Java
$JAVA_CC -version
$JAVA_CC gen-java/*.java -cp .:"lib/*"
$JAVA_CC *.java -cp .:gen-java/:"lib/*"
echo --- Done, now check out the run_storage_node.sh script!q
