package com.nju.mplab14.task4;

public class Task4 {

	private static final int iterCount = 5;

	public static void main(String []args) {

        String inPath = args[0]; //输入文件目录：邻接表（/wuxia-out/output3）
        String itrPath = args[1]; //迭代目录（/wuxia-out/output4-temp）
        String outPath = args[2]; //输出文件目录：最终迭代结果（/wuxia-out/output4）

        try{
            String []iterArgs = {inPath + "/part-r-00000", "", ""};

            for(int i = 0 ; i < iterCount ; i++ ){
                if( i != 0 )
                    iterArgs[1] = itrPath + "/iter" + i;
                else 
                    iterArgs[1] = inPath + "/part-r-00000";
                if( i < iterCount - 1 )
                    iterArgs[2] = itrPath + "/iter" + String.valueOf(i+1);
                else
                    iterArgs[2] = outPath;
                Task4Iter.main(iterArgs);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        

    }

}
