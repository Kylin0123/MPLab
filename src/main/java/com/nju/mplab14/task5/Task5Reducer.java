package com.nju.mplab14.task5;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.*;

public class Task5Reducer extends Reducer<Text, Text, Text, Text> {
	@Override
	protected void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException{
		//计数各label的总权重
		Map<String,Integer> labelMap = new HashMap<>();
		for(Text value:values){
			String []split = value.toString().split("\t");
			String label = split[0];
			Integer weight = Integer.valueOf(split[1]);
			if(labelMap.containsKey(label)){
				Integer oldValue = labelMap.get(label);
				labelMap.put(label, oldValue+weight);
			} else {
				labelMap.put(label, weight);
			}
		}

		//找到权重最大的几个label，随机选一个（task3中可以保证这里的key的values至少有一个）
		List<Map.Entry<String,Integer>> maxEntrys = new ArrayList<>();
		for(Map.Entry<String,Integer> entry:labelMap.entrySet()){
			if(maxEntrys.isEmpty() || entry.getValue().equals(maxEntrys.get(0).getValue())){
				maxEntrys.add(entry);
			}else if(entry.getValue()>maxEntrys.get(0).getValue()){
				maxEntrys.clear();
				maxEntrys.add(entry);
			}
		}
		Collections.shuffle(maxEntrys);

		//写入结果
		context.write(key, new Text(maxEntrys.get(0).getKey()));
	}
}
