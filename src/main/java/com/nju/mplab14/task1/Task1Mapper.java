package com.nju.mplab14.task1;

import java.util.ArrayList;
//import java.util.List;

import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.StringReader;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.fs.Path;

import org.ansj.domain.Term;
import org.ansj.library.DicLibrary;
//import org.ansj.library.UserDefineLibrary;
//import org.ansj.splitWord.Analysis;
import org.ansj.splitWord.analysis.ToAnalysis;
//import org.ansj.splitWord.analysis.BaseAnalysis;

public class Task1Mapper extends Mapper<LongWritable, Text, Text, LongWritable>{

    //private static ArrayList<String> names = new ArrayList<>();

    public void setup(Mapper.Context context){
        try{
            Path[] cacheFiles = context.getLocalCacheFiles();
            if(cacheFiles != null && cacheFiles.length >0){
                String line;
                //String[] tokens;
                BufferedReader joinReader = new BufferedReader(new FileReader(cacheFiles[0].toUri().getPath()));
                try{
                    while((line = joinReader.readLine()) != null){
                        if(!line.equals("")){
                            ;
                            //names.add(line);
                            //UserDefineLibrary.insertWord(line,"userDefine", 1000);
                            DicLibrary.insert(DicLibrary.DEFAULT,line,"userDefine", 1000);
                        }
                    }
                }finally{joinReader.close();}
            }
        }catch(IOException e){
            System.err.println("Exception reading DistributedCache:" + e);
        }
    }

    public void map(LongWritable key,Text value,Context context) throws IOException, InterruptedException {

        // String []tokens = value.toString().split(" ");

        // ArrayList<String> temp = new ArrayList<>();
        // String str = "";
 
        // for (String token : tokens) {
        //     for(String name : names){
        //         if(token.equals(name)){
        //             temp.add(token);
        //         }
        //     }
        // }
    
        ArrayList<String> temp = new ArrayList<>();
        String str = "";
        
        ToAnalysis udf = new ToAnalysis(new StringReader(value.toString()));
        Term term = null ;
		while((term=udf.next())!=null){
			if(term.getNatureStr() == "userDefine"){
                temp.add(term.getName());
            }
        }
       
        
        //List<Term> terms = ToAnalysis.parse(value.toString()).getTerms();
        //List<Term> terms = BaseAnalysis.parse(value.toString()).getTerms();
        // for(Term term : terms){
        //     if(term.getNatureStr() == "userDefine"){
        //         temp.add(term.getName());
        //     }
        // }

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
  
