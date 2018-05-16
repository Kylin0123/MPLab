package com.nju.mplab14;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.partition.HashPartitioner;

public class InvertedIndexPartitioner extends HashPartitioner<Text,LongWritable>{
    @Override
    public int getPartition(Text key, LongWritable value, int numReduceTasks) {
        String word_docId = key.toString();
        //get word
        String word = word_docId.split(",")[0];
        //hash according to word
        return super.getPartition(new Text(word), value, numReduceTasks);
    }
}