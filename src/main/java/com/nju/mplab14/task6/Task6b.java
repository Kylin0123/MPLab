package com.nju.mplab14.task6;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Task6b {
    public static void main(String[] args) {

        try {
            String outpath5 = args[0]; // input path: e.g. "/wuxia-out/output5";
            String outpath6b = args[1]; //output path: e.g. "/wuxia-out/output6b";
            Configuration jobconf = new Configuration();
            Job job = new Job(jobconf, "Task6b");

            job.setJarByClass(Task6b.class);
            job.setInputFormatClass(TextInputFormat.class);
            job.setMapperClass(Task6bMapper.class);
            job.setReducerClass(Task6bReducer.class);
            job.setMapOutputKeyClass(LongWritable.class);
            job.setMapOutputValueClass(Text.class);
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(Text.class);

            FileInputFormat.addInputPath(job, new Path(outpath5));
            FileOutputFormat.setOutputPath(job, new Path(outpath6b));
            job.waitForCompletion(true);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
