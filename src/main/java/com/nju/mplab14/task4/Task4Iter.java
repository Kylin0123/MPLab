package com.nju.mplab14.task4;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.util.*;
import java.io.*;
import java.net.URI;

public class Task4Iter {

    public static void main(String[] args) {
        try{

            Configuration conf = new Configuration();
            Job job = Job.getInstance(conf, "Task4");
            job.addCacheFile(new Path("/wuxia-out/output3/part-r-00000").toUri());

            job.setJarByClass(Task4Iter.class);
            job.setInputFormatClass(TextInputFormat.class);
            job.setOutputFormatClass(TextOutputFormat.class);
            job.setMapperClass(Task4Mapper.class);
            job.setReducerClass(Task4Reducer.class);
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(Text.class);
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(Text.class);
            FileInputFormat.addInputPath(job, new Path(args[0]));
            FileOutputFormat.setOutputPath(job, new Path(args[1]));
            job.waitForCompletion(true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}