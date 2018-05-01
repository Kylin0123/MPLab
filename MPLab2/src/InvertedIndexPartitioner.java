import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.lib.partition.HashPartitioner;

public class InvertedIndexPartitioner extends HashPartitioner<Text,LongWritable>{
    @Override
    public int getPartition(Text key, LongWritable value, int numReduceTasks) {
        String word_docId = key.toString();
        String word = word_docId.split(",")[0];
        return super.getPartition(new Text(word), value, numReduceTasks);
    }
}