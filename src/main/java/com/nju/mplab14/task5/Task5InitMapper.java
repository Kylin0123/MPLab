package com.nju.mplab14.task5;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class Task5InitMapper extends Mapper<LongWritable, Text, LongWritable, Text> {

	@Override
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		//所有的key都为0，以保证所有K-V对都由一个Reducer来串行处理
		context.write(new LongWritable(0), new Text(value.toString().split("\t")[0]));
	}
}
