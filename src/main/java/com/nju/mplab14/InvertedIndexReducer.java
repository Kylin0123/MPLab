package com.nju.mplab14;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.io.LongWritable;

import java.io.IOException;
import java.util.TreeMap;
import java.util.NavigableMap;
import java.util.Map;

public class InvertedIndexReducer extends Reducer<Text,LongWritable,Text,Text> {
    
    private static Text curWord = null;
    private NavigableMap<Text,LongWritable> map = new TreeMap<Text,LongWritable>();
    
    private String[] word_docId = null;
    private Text inputWord = null;
    private Text inputDocId = null;
    private LongWritable count = new LongWritable(0);

    @Override
    protected void reduce(Text key, Iterable<LongWritable> values, Context context)
        throws IOException, InterruptedException {

        word_docId = key.toString().split(",");
        inputWord = new Text(word_docId[0]);
        inputDocId = new Text(word_docId[1]);
        if(curWord == null)
            curWord = inputWord;
        else if( ! inputWord.equals(curWord)){
            dumpMap(context);          //dump map
            curWord = inputWord;     
        }
        count.set(0);
        for(LongWritable value : values) {
            count.set(count.get() + value.get());
        }
        map.put(inputDocId, count);
    }

    @Override
    protected void cleanup(Reducer<Text,LongWritable,Text,Text>.Context context)
        throws IOException, InterruptedException {

        dumpMap(context);              //dump map
    }

    private long nWords = 0;
    private long nDocs = 0;
    private StringBuilder all = new StringBuilder();

    protected void dumpMap(Reducer<Text,LongWritable,Text,Text>.Context context)
        throws IOException, InterruptedException {   

        all.setLength(0);
        nWords = 0;
        nDocs = 0;
        for(Map.Entry<Text, LongWritable> entry : map.entrySet()){
            all.append(entry.getKey() + ":" + entry.getValue() + (entry.equals(map.lastEntry()) ? "" : "; "));
            nWords += entry.getValue().get();
            nDocs += 1;
        }
        context.write(curWord, new Text(String.format("%.2f",(double)nWords/nDocs) + ", " + all.toString()));
        map.clear();
    }
}
