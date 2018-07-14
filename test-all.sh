#!/bin/bash
hadoop dfs -rm -r /wuxia-out/output2
hadoop dfs -rm -r /wuxia-out/output3
hadoop dfs -rm -r /wuxia-out/output3
hadoop dfs -rm -r /wuxia-out/output4
hadoop dfs -rm -r -f /wuxia-out/output4-temp
hadoop dfs -rm -r /wuxia-out/output5
hadoop dfs -rm -r /wuxia-out/output6
hadoop jar target/MPLab-1.0.jar com.nju.mplab14.Main
