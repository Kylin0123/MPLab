package com.nju.mplab14.task5;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Task5Init {
	public static void main(String []args){
		try {
			Configuration jobConf = new Configuration();
			Job job = Job.getInstance(jobConf, "task5init");

			job.setJarByClass(Task5Init.class);
			job.setInputFormatClass(TextInputFormat.class);
			job.setMapperClass(Task5InitMapper.class);
			job.setMapOutputKeyClass(LongWritable.class);
			job.setMapOutputValueClass(Text.class);
			job.setReducerClass(Task5InitReducer.class);
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(LongWritable.class);

			FileInputFormat.addInputPath(job, new Path(args[0])); //args[0]为上一次迭代结果文件名
			FileOutputFormat.setOutputPath(job, new Path(args[1])); //args[1]为这次迭代结果文件名
			job.waitForCompletion(true);
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
