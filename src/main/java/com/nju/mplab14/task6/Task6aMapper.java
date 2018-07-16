package com.nju.mplab14.task6;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class Task6aMapper extends Mapper<LongWritable, Text, DoubleWritable, Text> {
    @Override
    protected void map(LongWritable key, Text value, Context context)
        throws IOException, InterruptedException {

        String[] kv = value.toString().split("\t");
        String name = kv[0];
        double pagerank = Double.valueOf(kv[1]);
        context.write(new DoubleWritable(-pagerank), new Text(name));
    }
}
