package com.nju.mplab14.task4;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.util.*;
import java.io.*;
import java.net.URI;

public class Task4 {

	private static final int iterCount = 5;

	public static void main(String []args) {

        try{
            String []iterArgs = {"",""};

            for(int i = 0 ; i < iterCount ; i++ ){
                if( i != 0 )
                    iterArgs[0] = "/wuxia-out/output4-temp/iter" + i;
                else 
                    iterArgs[0] = "/wuxia-out/output3/part-r-00000";
                if( i < iterCount - 1 )
                    iterArgs[1] = "/wuxia-out/output4-temp/iter" + String.valueOf(i+1);
                else
                    iterArgs[1] = "/wuxia-out/output4";
                Task4Iter.main(iterArgs);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        

    }

}
