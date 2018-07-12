package com.nju.mplab14.task3;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class Task3Reducer extends Reducer<Text, Text, Text, Text> {

    private Map<String, LongWritable> map = new TreeMap<>();
    private String curKey = null;
    private long sum = 0;

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context)
        throws IOException, InterruptedException {

        String skey = key.toString();

        if(curKey == null)
            curKey = skey;
        else if(!key.equals(curKey)){
            dumpMap(context);
            curKey = skey;
        }

        for(Text value : values){
            String raw = value.toString();
            String[] list = raw.split(",");
            String svalue = list[0];
            long n = Long.valueOf(list[1]);
            if(!map.containsKey(svalue)){
                map.put(svalue, new LongWritable(n));
            }
            else{
                LongWritable t = map.get(svalue);
                map.put(svalue, new LongWritable(t.get() + n));
            }
            sum += n;
        }
        return;
    }

    private void dumpMap(Context context) throws IOException, InterruptedException {

        StringBuilder neighbor = new StringBuilder();
        neighbor.append('[');
        for( Map.Entry<String, LongWritable> entry : map.entrySet()) {
            String k = entry.getKey();
            long v = entry.getValue().get();
            neighbor.append(k + ",");
            neighbor.append((double)v / sum);
            neighbor.append("|");
        }
        neighbor.setCharAt(neighbor.length() - 1, ']');
        context.write(new Text(curKey), new Text(neighbor.toString()));
        sum = 0;
        map.clear();
    }

    @Override
    protected void cleanup(Context context)
        throws IOException, InterruptedException {

        dumpMap(context);
    }


}
