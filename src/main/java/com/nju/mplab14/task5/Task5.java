package com.nju.mplab14.task5;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.*;

public class Task5 {

	private static final int times = 1; //迭代次数

	public static void main(String []args) throws IOException {
		String inPath = "/wuxia-out/output3"; //输入文件：邻接表
		String outPath = "/wuxia-out/output5"; //输出文件：最终迭代结果

		//从HDFS下载输入文件
		FileSystem fs = FileSystem.get(new Configuration());
		fs.copyToLocalFile(new Path(inPath), new Path("local-output3"));

		//初始化迭代文件
		BufferedReader br = new BufferedReader(new FileReader("local-output3"));
		BufferedWriter bw = new BufferedWriter(new FileWriter("local-itr0"));
		String line;
		long labelCnt = 0;
		while((line=br.readLine())!=null){
			String []split = line.split("\t");
			String name = split[0]; //人名
			bw.write(name+"\t"+labelCnt+"\n");
			labelCnt++;
		}
		br.close();
		bw.close();

		//将初始迭代文件上传到HDFS
		fs.mkdirs(new Path("/wuxia-out/output5-itr"));
		fs.copyFromLocalFile(new Path("local-itr0"), new Path("/wuxia-out/output5-itr/itr0"));

		//开始迭代
		String []itrArgs = {inPath, "", ""};
		for(int i=0;i<times;i++){
			itrArgs[1] = "/wuxia-out/output5-itr/itr" + i;
			itrArgs[2] = "/wuxia-out/output5-itr/itr" + String.valueOf(i+1);
			Task5Iterator.main(itrArgs);
		}

		//TODO:此处还需要将最后一次迭代结果itrArgs[2]拷贝到outPath
	}
}
