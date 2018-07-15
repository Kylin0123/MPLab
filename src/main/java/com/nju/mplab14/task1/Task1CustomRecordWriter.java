package com.nju.mplab14.task1;

import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
 
public class Task1CustomRecordWriter extends RecordWriter<Text, Text> {
    
    private DataOutputStream fileOutstream;
 
    public Task1CustomRecordWriter(DataOutputStream fileOutstream) {
    	this.fileOutstream = fileOutstream;
    }

    @Override
    public void close(TaskAttemptContext arg0) throws IOException, InterruptedException {
    	fileOutstream.close();
    }
 
    @Override
    public void write(Text arg0, Text arg1) throws IOException, InterruptedException {
    	fileOutstream.write((arg0.toString() + "\n").getBytes());
    }
}
