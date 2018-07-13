package com.nju.mplab14.task5;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class Task5Reducer extends Reducer<Text, Text, Text, Text> {
	@Override
	protected void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException{
		//计数各label的总权重
		Map<String,Double> labelMap = new HashMap<>();
		for(Text value:values){
			String []split = value.toString().split("\t");
			String label = split[0];
			Double weight = Double.valueOf(split[1]);
			if(labelMap.containsKey(label)){
				Double oldValue = labelMap.get(label);
				labelMap.put(label, oldValue+weight);
			} else {
				labelMap.put(label, weight);
			}
		}

		//找到权重最大的label（task3中可以保证这里的key的values至少有一个）
		Map.Entry<String,Double> maxEntry = new AbstractMap.SimpleEntry<>("", -1.0);
		for(Map.Entry<String,Double> entry:labelMap.entrySet()){
			if(entry.getValue()>=maxEntry.getValue()){
				maxEntry = entry;
			}
		}

		//写入结果
		context.write(key, new Text(maxEntry.getKey()));
	}
}
