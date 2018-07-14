package com.nju.mplab14.task4;

import java.io.*;
import java.util.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class Task4Mapper extends Mapper<LongWritable, Text, Text, Text>{

    private final double initRank = 1.0;
    private Map<String, String> map = new HashMap<String, String>();

    public void setup(Mapper.Context context){
        try{
            Path[] cacheFiles = context.getLocalCacheFiles();
            Configuration conf = context.getConfiguration();
            if(cacheFiles != null && cacheFiles.length >0){
                String line;
                String []strs;  
                BufferedReader bufferReader = new BufferedReader(new FileReader(cacheFiles[0].toUri().getPath()));
                try{
                    while((line = bufferReader.readLine()) != null){
                        strs = line.split("\\t");                        
                        map.put(strs[0], strs[1].trim());
                    }
                }finally{bufferReader.close();}                
            }
         
        }catch(IOException e){
            System.err.println("Exception reading DistributedCache:" + e);
            e.printStackTrace();
        }
    }

    @Override
    protected void map(LongWritable key, Text value, Context context)
        throws IOException, InterruptedException {

        // TextInputFormat
        String []strs = value.toString().split("\\t");
        String name = strs[0];
        double curRank;     

        if( strs[1].charAt(0) == '[' )
            curRank = initRank;
        else
            curRank = Double.parseDouble(strs[1]);

        int length = map.get(name).length();

        String []nodes = map.get(name).substring( 1, length -1 ).split("\\|");
        String values[];
        String u;
        double probability;
        for( String node : nodes ){
            values = node.split(",");
            u = values[0];
            probability = Double.parseDouble( values[1] );
            context.write( new Text(u), new Text( String.valueOf(probability * curRank) ) );
        }
    }


}