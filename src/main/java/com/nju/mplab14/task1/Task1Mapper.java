package com.nju.mplab14.task1;

import java.util.ArrayList;

import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.fs.Path;

public class Task1Mapper extends Mapper<LongWritable, Text, Text, LongWritable>{

    private static ArrayList<String> names = new ArrayList<>();

    public void setup(Mapper.Context context){
        try{
            Path[] cacheFiles = context.getLocalCacheFiles();
            if(cacheFiles != null && cacheFiles.length >0){
                String line;
                String[] tokens;
                BufferedReader joinReader = new BufferedReader(new FileReader(cacheFiles[0].toString()));
                try{
                    while((line = joinReader.readLine()) != null){
                        if(!line.equals("")){
                            names.add(line);
                        }
                    }
                }finally{joinReader.close();}
            }
        }catch(IOException e){
            System.err.println("Exception reading DistributedCache:" + e);
        }
    }

    public void map(LongWritable key,Text value,Context context) throws IOException, InterruptedException {

        String []tokens = value.toString().split(" ");

        ArrayList<String> temp = new ArrayList<>();
        String str = "";
       
        for (String token : tokens) {
            for(String name : names){
                if(token.equals(name)){
                    temp.add(token);
                }
            }
        }
    

        if(temp.size() >= 2 && !((temp.get(0)).equals(temp.get(1)))) {
            for (String t : temp) {
                str = str + t + " ";
            }
        }

        if(!str.equals("")){
            context.write(new Text(str),new LongWritable(1));
        }
    }
}
  
