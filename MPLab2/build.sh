#!/bin/bash
javac -classpath /home/hadoop/hadoop_installs/hadoop-2.7.1/share/hadoop/common/hadoop-common-2.7.1.jar:/home/hadoop/hadoop_installs/hadoop-2.7.1/share/hadoop/common/lib/commons-cli-1.2.jar:/home/hadoop/hadoop_installs/hadoop-2.7.1/share/hadoop/mapreduce/hadoop-mapreduce-client-core-2.7.1.jar -d ./classes/ ./src/*.java
jar -cvf InvertedIndexer.jar -C ./classes/ .
