package com.nju.mplab14;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class InvertedIndexMapper extends Mapper<LongWritable,Text,Text,LongWritable> {
    @Override
    protected void map(LongWritable key, Text value, Context context)
        throws IOException, InterruptedException {
        //get filename
        FileSplit fileSplit = (FileSplit)context.getInputSplit();
        String fileName = fileSplit.getPath().getName();
        fileName = fileName.replace(".txt.segmented","");
        fileName = fileName.replace(".TXT.segmented","");
        //emit words in the document
        for(String word : value.toString().split(" ")){
            context.write(new Text(word+","+fileName), new LongWritable(1));
        }
    }
}
