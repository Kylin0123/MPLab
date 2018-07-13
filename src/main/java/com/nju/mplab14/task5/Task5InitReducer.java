package com.nju.mplab14.task5;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class Task5InitReducer extends Reducer<LongWritable, Text, Text, LongWritable> {

	private long labelCnt = 0;

	@Override
	protected void reduce(LongWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
		//所有K-V都在同一个Reducer上处理
		for(Text value:values){
			context.write(value, new LongWritable(labelCnt));
			labelCnt++;
		}
	}
}
