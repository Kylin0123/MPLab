package com.nju.mplab14.task3;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class Task3Mapper extends Mapper<LongWritable, Text, Text, Text> {
    @Override
    protected void map(LongWritable key, Text value, Context context)
        throws IOException, InterruptedException {

        String lineContent = value.toString();
        String[] kv = lineContent.split("\t");
        String[] people = getPeople(kv[0]); // "<p1,p2>" => [p1,p2]
        long times = Long.valueOf(kv[1]); // "1" => 1

        for(int i = 0; i < times; i++){
            context.write(new Text(people[0]), new Text(people[1]));
        }

    }
    String[] getPeople(String string){
        int len = string.length();
        string = string.substring(1, len-1);
        return string.split(",");
    }
}
