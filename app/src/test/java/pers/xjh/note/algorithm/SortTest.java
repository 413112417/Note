package pers.xjh.note.algorithm;

import android.text.TextUtils;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

/**
 * Created by XJH on 2017/5/2.
 */
public class SortTest {

    Random random;

    @Before
    public void setUp() throws Exception {
        random = new Random();
    }

    @Test
    public void insertSort() throws Exception {
//        int[] data = new int[1000000];
//        for(int i=0; i<data.length; i++) {
//            data[i] = random.nextInt();
//        }
//        long start = System.currentTimeMillis();
//        Sort.shellSort(data, false);
//        long end = System.currentTimeMillis();
//        System.out.print("selectSort:" + (end - start) + "\n");

        String number = "DDF818C6";

        //先去掉前两位
        number = number.substring(2, number.length());

        StringBuilder sb = new StringBuilder();

        while (number != null && !"".equals(number)) {
            sb.append(number.substring(number.length() - 2, number.length()));
            number = number.substring(0, number.length() - 2);
        }
//                String str_1 = number.substring(2, 4);
//                String str_2 = number.substring(4, 6);
//                String str_3 = number.substring(6, 8);
//                String str_4 = number.substring(8, 10);

//                number = str_4 + str_3 + str_2 + str_1;

        int result = Integer.valueOf(sb.toString(), 16);
    }
}