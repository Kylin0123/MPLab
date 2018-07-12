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

        if( strs[1][0] == '[' ){ // NodeList， 把这个字符串存入DC
            NodeList = strs[1];
            curRank = initRank;
        }
        else{ // curRank，从DC中去除NodeList
            curRank = Double.parseDouble(strs[1]);
        }

        
    }
}