package com.nju.mplab14;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.TableDescriptorBuilder;
import org.apache.hadoop.hbase.client.TableDescriptor;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptorBuilder;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.TableName;

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

    private Connection connection = null;
    private Table table = null;

    @Override
    protected void setup(Reducer<Text,LongWritable,Text,Text>.Context context)
        throws IOException,InterruptedException {

        Configuration conf = HBaseConfiguration.create();  
        conf.set("hbase.zookeeper.quorum", "localhost");  
        conf.set("hbase.zookeeper.property.clientPort", "2181");   

        connection = ConnectionFactory.createConnection(conf);
        Admin admin = connection.getAdmin();

        TableName tableName = TableName.valueOf("Wuxia");

        if( admin.tableExists(tableName) ){ //如果表格存在就删除
            admin.disableTable(tableName);
            admin.deleteTable(tableName);    
        }
        
        // 创建表格
        TableDescriptor tableDesc = TableDescriptorBuilder.newBuilder(tableName).addColumnFamily(ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes("avgCnt")).build()).build();
        admin.createTable(tableDesc);

        // 建立连接
        table = connection.getTable(tableName);
       
    }

    private void putInDataBase(String key, String value){
        if(key.length() == 0)
            return;
        Put put = new Put(Bytes.toBytes(key));
        put.addColumn( Bytes.toBytes("avgCnt"), Bytes.toBytes(""), Bytes.toBytes(value) );
        try{
            table.put(put);
        }
        catch(IOException e){

        }
        catch(IllegalArgumentException ex){

        }
    }

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
        
        LongWritable count = new LongWritable(0);
        for(LongWritable value : values) {
            
            count.set(count.get() + value.get());
        }
        map.put(inputDocId, count);
    }

    @Override
    protected void cleanup(Reducer<Text,LongWritable,Text,Text>.Context context)
        throws IOException, InterruptedException {

        dumpMap(context);              //dump map

        table.close();
        connection.close();
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
        String argCnt = String.format("%.2f",(double)nWords/nDocs);
        context.write(curWord, new Text(argCnt + ", " + all.toString()));
        map.clear();

        // 填入数据库
        putInDataBase(curWord.toString(),argCnt);
    }
}
