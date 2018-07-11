package com.nju.mplab14.task2;

import java.io.IOException;
import java.util.*;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class Task2Mapper extends Mapper<LongWritable, Text, Text, LongWritable>{
    
    Set<String> s = new HashSet();

    @Override
    protected void map(LongWritable key, Text value, Context context)
        throws IOException, InterruptedException {

        s.clear();
        
        // TextInputFormat
        String []names = value.toString().split(" ");
        
        for( String name1 : names )
            for( String name2 : names )
                if( ! name1.equals(name2) ){
                    String str = "<"+name1+","+name2+">";
                    if( ! s.contains(str) )
                        s.add(str);
                }
        
        for( String str : s )
            context.write( new Text(str), new LongWritable(1) );

    }
}