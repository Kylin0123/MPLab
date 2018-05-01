import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.io.LongWritable;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class InvertedIndexReducer extends Reducer<Text,LongWritable,Text,Text> {
    
    private static Text curWord = null;

    private Map<Text,LongWritable> map = new HashMap<Text,LongWritable>();
    
    @Override
    protected void reduce(Text key, Iterable<LongWritable> values, Context context)
        throws IOException, InterruptedException {
        String[] word_docId = key.toString().split(",");
        Text word = new Text(word_docId[0]);
        Text docId = new Text(word_docId[1]);
        if(curWord == null)
            curWord = word;
        if(word.equals(curWord)){
            LongWritable count = new LongWritable(0);
            for(LongWritable value : values) {
                count.set(count.get() + value.get());
            }
            map.put(docId, count);
        }
        else{
            //dump map
            StringBuilder all = new StringBuilder();
            long nWords = 0;
            long nDocs = 0;
            for(Map.Entry<Text, LongWritable> entry : map.entrySet()){
                all.append(entry.getKey() + ":" + entry.getValue() + "; ");
                nWords += entry.getValue().get();
                nDocs += 1;
            }
            double rate = (double)nWords/nDocs;
            context.write(curWord, new Text(String.format("%.2f",rate) + ", " + all.toString()));
            map.clear();
            //get new word
            curWord = word;
            LongWritable count = new LongWritable(0);
            for(LongWritable value : values) {
                count.set(count.get() + value.get());
            }
            map.put(docId, count);
        }
    }

    @Override
    protected void cleanup(Reducer<Text,LongWritable,Text,Text>.Context context)
        throws IOException, InterruptedException {
        StringBuilder all = new StringBuilder();
        for(Map.Entry<Text, LongWritable> entry : map.entrySet()){
            all.append(entry.getKey() + ":" + entry.getValue() + ";");
        }
        context.write(curWord, new Text(all.toString()));
        map.clear();
    }
}
