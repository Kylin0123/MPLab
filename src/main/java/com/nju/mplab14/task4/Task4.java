package com.nju.mplab14.task4;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.util.*;
import java.io.*;
import java.net.URI;

public class Task4 {
    public static void main(String[] args) {
        try{
            Configuration conf = new Configuration();
            Job job = Job.getInstance(conf, "Task4");

            // 在/etc/hadoop/core-site.xml中设置
            String default_name = "hdfs://localhost:54310";
            conf.set("fs.default.name",default_name); 
            FileSystem fileSystem = FileSystem.get(new URI(default_name), conf );
            List<Path> paths = getFilesUnderFolder(fileSystem, new Path("/wuxia-out/output3"), "part-r-00000");
            assert paths.size() == 1;
    
            // Read output3 into DC
            //job.addCacheFile( new URI(paths.get(0).toString()) );
            job.addCacheFile(new Path("/wuxia-in/People_List_unique.txt").toUri() );

            // count the number in output3 into the N
            BufferedReader bufferedReader = new BufferedReader(new FileReader(paths.get(0).toString()));
            String str = null;
            int N = 0;
            while((str = bufferedReader.readLine()) != null){            
                N ++;
            }
            conf.setInt("N", N);
            bufferedReader.close();

            String output1 = "/wuxia-out/output3";
            String output2 = "/wuxia-out/output4";
            job.setJarByClass(Task4.class);
            job.setInputFormatClass(TextInputFormat.class);
            job.setOutputFormatClass(TextOutputFormat.class);
            job.setMapperClass(Task4Mapper.class);
            job.setReducerClass(Task4Reducer.class);
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(Text.class);
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(Text.class);
            FileInputFormat.addInputPath(job, new Path(output1));
            FileOutputFormat.setOutputPath(job, new Path(output2));
            job.waitForCompletion(true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static List<Path> getFilesUnderFolder(FileSystem fs, Path folderPath, String pattern) throws IOException {
        List<Path> paths = new ArrayList<Path>();
        if (fs.exists(folderPath)) {
            FileStatus[] fileStatus = fs.listStatus(folderPath);
            for (int i = 0; i < fileStatus.length; i++) {
                FileStatus fileStatu = fileStatus[i];
                if (!fileStatu.isDir()) {//只要文件
                    Path oneFilePath = fileStatu.getPath();
                    if (pattern == null) 
                        paths.add(oneFilePath);
                    else if (oneFilePath.getName().contains(pattern)) 
                        paths.add(oneFilePath);
                }
            }
        }
        return paths;
    }     
}