package pers.xjh.note.algorithm;

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
        int[] data = new int[1000000];
        for(int i=0; i<data.length; i++) {
            data[i] = random.nextInt();
        }
        long start = System.currentTimeMillis();
        Sort.shellSort(data, false);
        long end = System.currentTimeMillis();
        System.out.print("selectSort:" + (end - start) + "\n");
    }
}