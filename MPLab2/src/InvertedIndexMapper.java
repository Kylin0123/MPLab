import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class InvertedIndexMapper extends Mapper<LongWritable,Text,Text,LongWritable> {
    @Override
    protected void map(LongWritable key, Text value, Context context)
        throws IOException, InterruptedException {
        FileSplit fileSplit = (FileSplit)context.getInputSplit();
        String fileName = fileSplit.getPath().getName();
        fileName = fileName.replace(".txt.segmented","");
        StringTokenizer itr = new StringTokenizer(value.toString());
        for(;itr.hasMoreElements();) {
            Text word_docId = new Text(itr.nextToken() + "," + fileName);
            context.write(word_docId,new LongWritable(1));
        }
    }
}
