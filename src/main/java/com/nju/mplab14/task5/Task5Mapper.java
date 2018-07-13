package com.nju.mplab14.task5;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.*;

public class Task5Mapper extends Mapper<LongWritable, Text, Text, Text> {

	private Map<String, List<Map.Entry<String, Double>>> mp = new HashMap<>();

	@Override
	protected void setup(Context context) throws IOException, InterruptedException {
		Path []cacheFiles = context.getLocalCacheFiles();
		BufferedReader br = new BufferedReader(new FileReader(cacheFiles[0].toUri().getPath())); //邻接表文件
		String line;
		while((line=br.readLine())!=null){
			String []split = line.split("\t");
			String name = split[0];
			List<Map.Entry<String,Double>> neighborList = new ArrayList<>();
			String list = split[1];
			list = list.substring(1, list.length()-1);
			String []neighbors = list.split("\\|");
			for(String neighbor:neighbors){
				String []pair = neighbor.split(",");
				String neighborName = pair[0];
				Double weight = Double.valueOf(pair[1]);
				neighborList.add(new AbstractMap.SimpleEntry<>(neighborName, weight));
			}
			mp.put(name, neighborList);
		}
		br.close();
	}

	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		String []pair = value.toString().split("\t");
		String name = pair[0];
		String label = pair[1];
		for(Map.Entry<String, Double> neighbor:mp.get(name)){
			context.write(
					new Text(neighbor.getKey()), //Key:邻居名
					new Text(label+"\t"+String.valueOf(neighbor.getValue()))); //Value:类别\t权重
		}
	}

}
