hdfs dfs -rm -r /user/2018st14/wuxia-out
peoplelist="/data/task2/people_name_list.txt"
input1="/data/task2/novels"
output1="/user/2018st14/wuxia-out/output1"
output2="/user/2018st14/wuxia-out/output2"
output3="/user/2018st14/wuxia-out/output3"
output4itr="/user/2018st14/wuxia-out/output4-itr"
output4="/user/2018st14/wuxia-out/output4"
output5itr="/user/2018st14/wuxia-out/output5-itr"
output5="/user/2018st14/wuxia-out/output5"
output6a="/user/2018st14/wuxia-out/output6a"
output6b="/user/2018st14/wuxia-out/output6b"
hadoop jar MPLab-1.0.jar com.nju.mplab14.Main $peoplelist $input1 $output1 $output2 $output3 $output4itr $output4 $output5itr $output5 $output6a $output6b
