package com.nju.mplab14.task5;

import java.io.*;

public class Task5 {

	private static final int times = 20; //迭代次数

	public static void main(String []args) {
		try {
			String inPath = "/wuxia-out/output3"; //输入文件：邻接表
			String outPath = "/wuxia-out/output5"; //输出文件：最终迭代结果

			//初始化迭代文件
			String[] initArgs = {inPath, "/wuxia-out/output5-itr/itr0"};
			Task5Init.main(initArgs);

			//开始迭代
			//TODO：这里的itrArgs[0]依赖于output3中的输出文件名，output3应当有且仅有一个结果文件
			String[] itrArgs = {inPath + "/part-r-00000", "", ""};
			for (int i = 0; i < times; i++) {
				itrArgs[1] = "/wuxia-out/output5-itr/itr" + i;
				if (i < times - 1) {
					itrArgs[2] = "/wuxia-out/output5-itr/itr" + String.valueOf(i + 1);
				} else {
					itrArgs[2] = outPath; //最后一次迭代结果直接放到outPath中
				}
				Task5Iterator.main(itrArgs);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
