package com.nju.mplab14.task4;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class Task4Reducer extends Reducer<Text,Text,Text,Text>{

    final double d = 0.8;

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context)
        throws IOException, InterruptedException {

        

    }

}