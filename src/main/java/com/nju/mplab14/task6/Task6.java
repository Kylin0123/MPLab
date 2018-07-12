package com.nju.mplab14.task6;

import com.nju.mplab14.task6.Task6;
import com.nju.mplab14.task6.Task6Mapper;
import com.nju.mplab14.task6.Task6Reducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Task6 {
    public static void main(String[] args) {

        try {
            String outpath4 = "/wuxia-out/output4";
            String outpath6 = "/wuxia-out/output6";
            Configuration jobconf6 = new Configuration();
            Job job6 = new Job(jobconf6, "task6");

            job6.setJarByClass(Task6.class);
            job6.setInputFormatClass(TextInputFormat.class);
            job6.setMapperClass(Task6Mapper.class);
            job6.setReducerClass(Task6Reducer.class);
            job6.setMapOutputKeyClass(DoubleWritable.class);
            job6.setMapOutputValueClass(Text.class);
            job6.setOutputKeyClass(Text.class);
            job6.setOutputValueClass(Text.class);

            FileInputFormat.addInputPath(job6, new Path(outpath4));
            FileOutputFormat.setOutputPath(job6, new Path(outpath6));
            job6.waitForCompletion(true);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
