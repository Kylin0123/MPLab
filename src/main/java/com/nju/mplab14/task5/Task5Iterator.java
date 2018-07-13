package com.nju.mplab14.task5;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Task5Iterator {
	public static void main(String []args){
		try {
			Configuration jobConf5 = new Configuration();
			Job job5 = Job.getInstance(jobConf5, "task5");

			job5.setJarByClass(Task5Iterator.class);
			job5.setInputFormatClass(TextInputFormat.class);
			job5.setMapperClass(Task5Mapper.class);
			job5.setReducerClass(Task5Reducer.class);
			job5.setMapOutputKeyClass(Text.class);
			job5.setMapOutputValueClass(Text.class);
			job5.setOutputKeyClass(Text.class);
			job5.setOutputValueClass(Text.class);
			job5.addCacheFile(new Path(args[0]).toUri()); //args[0]为邻接表文件

			FileInputFormat.addInputPath(job5, new Path(args[1])); //args[1]为上一次迭代结果文件名
			FileOutputFormat.setOutputPath(job5, new Path(args[2])); //args[2]为这次迭代结果文件名
			job5.waitForCompletion(true);
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
