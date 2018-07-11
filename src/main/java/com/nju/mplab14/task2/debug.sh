#!/bin/bash
hadoop dfs -rm -r -f /wuxia-out/output2
mvn clean package
hadoop jar target/MPLab-1.0.jar com.nju.mplab14.task2.Task2
hadoop dfs -cat /wuxia-out/output2/part* | less