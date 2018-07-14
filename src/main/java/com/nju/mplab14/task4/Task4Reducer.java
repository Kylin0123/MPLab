package com.nju.mplab14.task4;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.Reducer;

public class Task4Reducer extends Reducer<Text,Text,Text,Text>{

    @Override
    protected void setup(Reducer<Text,Text,Text,Text>.Context context)
        throws IOException,InterruptedException {
        Configuration conf = context.getConfiguration();
    }

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context)
        throws IOException, InterruptedException {

        double newRank = 0;
        for( Text value : values ){
            newRank += Double.parseDouble(value.toString());
        }
        context.write(key,  new Text(String.valueOf(newRank) ) );
    }

}