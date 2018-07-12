package com.nju.mplab14.task4;

import java.io.IOException;
import java.util.*;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class Task4Mapper extends Mapper<LongWritable, Text, Text, Text>{

    final double initRank = 0.5;

    @Override
    protected void map(LongWritable key, Text value, Context context)
        throws IOException, InterruptedException {

        // TextInputFormat

        String []strs = value.toString().split("\t");
        String name = strs[0];
        String NodeList;
        double curRank;

        if( strs[1].charAt(0) == '[' ){
            curRank = initRank;
            NodeList = strs[1];
        }
        else{
            curRank = Double.parseDouble(strs[1]);
            NodeList = strs[2];
        }

        String []nodes = NodeList.substring( 1, NodeList.length()-1 ).split("|");
        String values[];
        String u;
        String probabilityStr;
        double probability;
        for( String node : nodes ){
            values = node.split(",");
            u = values[0];
            probabilityStr = values[1];
            probability = Double.parseDouble(probabilityStr);
            context.write( new Text(u), new Text( String.valueOf(probability * curRank) ) );
        }

        context.write( new Text(name), new Text(NodeList) );
    }
}