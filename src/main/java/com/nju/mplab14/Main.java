package com.nju.mplab14;

import com.nju.mplab14.task1.Task1;
import com.nju.mplab14.task2.Task2;
import com.nju.mplab14.task3.Task3;
import com.nju.mplab14.task4.Task4;
import com.nju.mplab14.task5.Task5;
import com.nju.mplab14.task6.Task6a;
import com.nju.mplab14.task6.Task6b;

public class Main {
    public static void main(String[] args) {
        /*
         *  args: [people_list, input1, output1, output2, output3,
         *  output4-itr, output4, output5-itr, output5, output6a, output6b]
         */

        /* people_list, input1, output1 */
        Task1.main(new String[] {args[0], args[1], args[2]});

        /* output1, output2 */
        Task2.main(new String[] {args[2], args[3]});

        /* output2, output3 */
        Task3.main(new String[] {args[3], args[4]});

        /* output3, output4-itr, output4 */
        Task4.main(new String[] {args[4], args[5], args[6]});

        /* output3, output5-itr, output5 */
        Task5.main(new String[] {args[4], args[7], args[8]});

        /* output4, output6a */
        Task6a.main(new String[] {args[6], args[9]});

        /* output5, output6b*/
        Task6b.main(new String[] {args[8], args[10]});
    }
}
