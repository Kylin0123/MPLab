#!/bin/bash
mkdir classes
#HDOOP_HOME=/home/hadoop/hadoop-2.7.1
javac -source 1.7 -target 1.7 -classpath $HADOOP_HOME/share/hadoop/common/hadoop-common-2.7.1.jar:$HADOOP_HOME/share/hadoop/common/lib/commons-cli-1.2.jar:$HADOOP_HOME/share/hadoop/mapreduce/hadoop-mapreduce-client-core-2.7.1.jar -d ./classes/ ./src/*.java
jar -cvf InvertedIndexer.jar -C ./classes/ .
