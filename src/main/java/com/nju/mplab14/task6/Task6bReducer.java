package com.nju.mplab14.task6;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class Task6bReducer extends Reducer<LongWritable, Text, Text, Text> {

    private long cur = 0;
    private long cnt = 0;

    @Override
    protected void reduce(LongWritable key, Iterable<Text> values, Context context)
        throws IOException, InterruptedException {

        if (cur != key.get()) {
            cur = key.get();
            cnt++;
        }

        for(Text value : values){
            context.write(value, new Text(String.valueOf(cnt)));
        }
    }
}
