package com.nju.mplab14.task6;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class Task6bMapper extends Mapper<LongWritable, Text, LongWritable, Text> {
    @Override
    protected void map(LongWritable key, Text value, Context context)
        throws IOException, InterruptedException {
        String[] kv = value.toString().split("\t");
        context.write(new LongWritable(Long.valueOf(kv[1])), new Text(kv[0]));
    }
}
