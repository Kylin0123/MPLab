#!/bin/bash
hadoop dfs -rm -r -f /wuxia-out/output1
mvn clean package
hadoop jar target/MPLab-1.0.jar com.nju.mplab14.task1.Task1
rm -f getfile
hadoop dfs -get /wuxia-out/output1/part* getfile
