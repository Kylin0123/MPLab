package com.nju.mplab14.task6;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class Task6aReducer extends Reducer<DoubleWritable, Text, Text, DoubleWritable> {
    protected void reduce(DoubleWritable key, Iterable<Text> values, Context context)
        throws IOException, InterruptedException {

        for(Text value : values){
            context.write(value, new DoubleWritable(-key.get()));
        }
    }
}
