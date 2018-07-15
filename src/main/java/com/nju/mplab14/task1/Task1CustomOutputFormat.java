package com.nju.mplab14.task1;
 
import java.io.IOException;
 
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
 
public class Task1CustomOutputFormat extends FileOutputFormat<Text, Text> {
    @Override
    public RecordWriter<Text, Text> getRecordWriter(TaskAttemptContext arg0) throws IOException, InterruptedException {
	
        Path path = FileOutputFormat.getOutputPath(arg0);

        Path fullfilePath = new Path(path, "part0");
    
        FileSystem fs = path.getFileSystem(arg0.getConfiguration());
        FSDataOutputStream fileOutstream = fs.create(fullfilePath, arg0);
		
        return new Task1CustomRecordWriter(fileOutstream);
    }
 
}
