#!/bin/bash
hdfs dfs -rm -r /wuxia-out
hadoop jar InvertedIndexer.jar InvertedIndexer /wuxia-in /wuxia-out
