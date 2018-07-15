package com.nju.mplab14.task1;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
//import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.fs.Path;


public class Task1{
    public static void main(String[] args){
        try{
            Configuration conf = new Configuration();

            Job job = new Job(conf, "Task1");
            //job.addCacheFile(new Path("/user/2018st14/wuxia-in/people_name_list.txt").toUri());
			job.addCacheFile(new Path(args[0]).toUri());
            job.setJarByClass(Task1.class);

            //String input = "/user/2018st14/wuxia-in/input1";
            String input = args[1];
			//String output = "/user/2018st14/wuxia-out/output1";
			String output = args[2];
            FileInputFormat.addInputPath(job, new Path(input));
            FileOutputFormat.setOutputPath(job, new Path(output));            

            job.setMapperClass(Task1Mapper.class);
            //job.setCombinerClass(Task1Combiner.class);
            job.setReducerClass(Task1Reducer.class);
            
            job.setInputFormatClass(TextInputFormat.class);
            job.setOutputFormatClass(Task1CustomOutputFormat.class);
            
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(LongWritable.class);
            
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(Text.class);
            
            //job.setNumReduceTasks(0);
            
            job.waitForCompletion(true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}