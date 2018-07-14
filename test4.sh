#!/bin/bash
hadoop dfs -rm -r -f /wuxia-out/output4-temp
hadoop dfs -rm -r -f /wuxia-out/output4
rm -rf output4-temp
rm -rf output4
mvn clean package
hadoop jar target/MPLab-1.0.jar com.nju.mplab14.task4.Task4
hadoop dfs -copyToLocal /wuxia-out/output4-temp ./
hadoop dfs -copyToLocal /wuxia-out/output4 ./