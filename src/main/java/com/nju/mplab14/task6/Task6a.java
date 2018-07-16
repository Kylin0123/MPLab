package com.nju.mplab14.task6;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Task6a {
    public static void main(String[] args) {

        try {
            String outpath4 = args[0]; // input path: e.g. "/wuxia-out/output4";
            String outpath6a = args[1]; //output path: e.g. "/wuxia-out/output6a";
            Configuration jobconf6 = new Configuration();
            Job job6 = new Job(jobconf6, "Task6a");

            job6.setJarByClass(Task6a.class);
            job6.setInputFormatClass(TextInputFormat.class);
            job6.setMapperClass(Task6aMapper.class);
            job6.setReducerClass(Task6aReducer.class);
            job6.setMapOutputKeyClass(DoubleWritable.class);
            job6.setMapOutputValueClass(Text.class);
            job6.setOutputKeyClass(Text.class);
            job6.setOutputValueClass(Text.class);

            FileInputFormat.addInputPath(job6, new Path(outpath4));
            FileOutputFormat.setOutputPath(job6, new Path(outpath6a));
            job6.waitForCompletion(true);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
