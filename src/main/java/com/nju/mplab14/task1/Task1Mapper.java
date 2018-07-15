package com.nju.mplab14.task1;

import org.ansj.domain.Term;
import org.ansj.library.DicLibrary;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Task1Mapper extends Mapper<LongWritable, Text, Text, LongWritable>{
    private static Set<String> names = new HashSet<>();
    public void setup(Mapper.Context context){
        try{
            Path[] cacheFiles = context.getLocalCacheFiles();
            if(cacheFiles != null && cacheFiles.length >0){
                String line;
                BufferedReader joinReader = new BufferedReader(new FileReader(cacheFiles[0].toUri().getPath()));
                try{
                    while((line = joinReader.readLine()) != null){
                        if(!line.equals("")){
                            names.add(line);
                            DicLibrary.insert(DicLibrary.DEFAULT,line,"nr", 10000000);
                        }
                    }
                }finally{joinReader.close();}
            }
        }catch(IOException e){
            System.err.println("Exception reading DistributedCache:" + e);
        }
    }
    public void map(LongWritable key,Text value,Context context) throws IOException, InterruptedException {
        ArrayList<String> temp = new ArrayList<>();
        String str = "";      
        List<Term> terms = ToAnalysis.parse(value.toString()).getTerms();
        for(Term term : terms){
            if(names.contains(term.getName()))
                temp.add(term.getName());
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
  
