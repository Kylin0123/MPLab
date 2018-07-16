package com.nju.mplab14.task3;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Task3 {
    public static void main(String[] args) {
        try {
            String outpath2 = args[0]; // input path: e.g. "/wuxia-out/output2"
            String outpath3 = args[1]; // output path: e.g. "/wuxia-out/output3"
            Configuration conf = new Configuration();
            Job job = new Job(conf, "Task3");

            job.setJarByClass(Task3.class);
            job.setInputFormatClass(TextInputFormat.class);
            job.setMapperClass(Task3Mapper.class);
            job.setReducerClass(Task3Reducer.class);
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(Text.class);
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(Text.class);

            //set num of reduce tasks to 1
            job.setNumReduceTasks(1);

            FileInputFormat.addInputPath(job, new Path(outpath2));
            FileOutputFormat.setOutputPath(job, new Path(outpath3));
            job.waitForCompletion(true);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
