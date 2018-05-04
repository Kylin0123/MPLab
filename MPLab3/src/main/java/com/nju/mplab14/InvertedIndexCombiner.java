package com.nju.mplab14;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class InvertedIndexCombiner extends Reducer<Text,LongWritable,Text,LongWritable>{

    private LongWritable count = new LongWritable(0);

    @Override
    protected void reduce(Text key, Iterable<LongWritable> values, Context context)
        throws IOException, InterruptedException {
        //count num of values
        count.set(0);
        for(LongWritable value : values) {
            count.set(count.get() + value.get());
        }
        context.write(key, count);
    }
}