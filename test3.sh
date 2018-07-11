#!/bin/bash
hadoop dfs -rm -r /wuxia-out/output3
hadoop jar target/MPLab-1.0.jar com.nju.mplab14.task3.Task3
hadoop dfs -cat /wuxia-out/output3/part*
