package com.nju.mplab14;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.BasicConfigurator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.NavigableMap;

public class HbaseScanner {
    public static void main(String[] args) throws IOException {

        BasicConfigurator.configure();
        /*get the handle of file*/
        File file = new File("./out.txt");
        if(!file.exists()) {
            file.createNewFile();
        }
        /*get output stream of file*/
        FileOutputStream fileOutputStream = new FileOutputStream(file);

        /*get configurations*/
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "localhost");
        conf.set("hbase.zookeeper.property.clientPort", "2181");
        Connection connection = ConnectionFactory.createConnection(conf);

        /*connect to table*/
        Table table = connection.getTable(TableName.valueOf("Wuxia"));

        /*get the scanner of table*/
        Scan scan = new Scan();
        ResultScanner resultScanner = table.getScanner(scan);

        /*scan*/
        for (Result result : resultScanner) {
            String rowKey = Bytes.toString(result.getRow());
            NavigableMap<byte[], NavigableMap<byte[], byte[]>> familyMap = result.getNoVersionMap();
            for (byte[] fByte : familyMap.keySet()) {
                NavigableMap<byte[], byte[]> quaMap = familyMap.get(fByte);
                // String familyName = Bytes.toString(fByte);
                for (Map.Entry<byte[], byte[]> entryByte : quaMap.entrySet()) {
                    /*convert byte[] to String*/
                    // String quaName = Bytes.toString(entryByte.getKey());
                    String value = Bytes.toString(entryByte.getValue());
                    /*output to screen*/
                    String rst = rowKey + '\t' + value + '\n';
                    fileOutputStream.write(rst.toString().getBytes());
                }
            }
        }

        // close all
        resultScanner.close();
        table.close();
        connection.close();
        fileOutputStream.close();
    }
}