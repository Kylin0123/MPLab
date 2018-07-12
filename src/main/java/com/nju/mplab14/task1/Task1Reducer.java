package com.nju.mplab14.task1;
import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class Task1Reducer extends Reducer<Text, LongWritable, Text, Text>{
    public void reduce(Text key,Iterable<LongWritable>values,Context context) throws IOException, InterruptedException {
        for(LongWritable v : values){
            context.write(key, new Text(""));
        }
    }
}
