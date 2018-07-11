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
            String outpath2 = "/wuxia-out/output2";
            String outpath3 = "/wuxia-out/output3";
            Configuration jobconf3 = new Configuration();
            Job job3 = new Job(jobconf3, "task3");

            job3.setJarByClass(Task3.class);
            job3.setInputFormatClass(TextInputFormat.class);
            job3.setMapperClass(Task3Mapper.class);
            job3.setReducerClass(Task3Reducer.class);
            job3.setMapOutputKeyClass(Text.class);
            job3.setMapOutputValueClass(Text.class);
            job3.setOutputKeyClass(Text.class);
            job3.setOutputValueClass(Text.class);

            FileInputFormat.addInputPath(job3, new Path(outpath2));
            FileOutputFormat.setOutputPath(job3, new Path(outpath3));
            job3.waitForCompletion(true);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
