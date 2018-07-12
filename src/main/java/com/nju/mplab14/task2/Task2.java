package com.nju.mplab14.task2;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Task2 {
    public static void main(String[] args) {
        try{
            Configuration conf = new Configuration();
            Job job = new Job(conf, "Task2");
            String output1 = "/wuxia-out/output1";
            String output2 = "/wuxia-out/output2";
            job.setJarByClass(Task2.class);
            job.setInputFormatClass(TextInputFormat.class);
            //job.setOutputFormatClass(TextOutputFormat.class);
            job.setMapperClass(Task2Mapper.class);
            job.setCombinerClass(Task2Combiner.class);
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(LongWritable.class);
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(LongWritable.class);
            FileInputFormat.addInputPath(job, new Path(output1));
            FileOutputFormat.setOutputPath(job, new Path(output2));
            job.waitForCompletion(true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}