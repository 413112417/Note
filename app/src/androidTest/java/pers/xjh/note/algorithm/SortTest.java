package pers.xjh.note.algorithm;

import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Created by xjh on 17-12-20.
 */
@MediumTest
@RunWith(AndroidJUnit4.class)
public class SortTest {

    int[] mData;

    @Before
    public void setUp() throws Exception {
        mData = new int[]{2,1,5,3,6,7,8,4,9};
    }

    @Test
    public void selectSort() throws Exception {
        long start = System.currentTimeMillis();
        Sort.selectSort(mData, false);
        long end = System.currentTimeMillis();
        System.out.print("selectSort:" + (end - start) + "\n");

        int[] result = new int[] {1,2,3,4,5,6,7,8,9};
        assertEquals(mData[5], result[5]);
    }

    @Test
    public void insertSort() throws Exception {
        long start = System.currentTimeMillis();
        Sort.insertSort(mData, false);
        long end = System.currentTimeMillis();
        System.out.print("insertSort:" + (end - start) + "\n");

        int[] result = new int[] {1,2,3,4,5,6,7,8,9};
        assertEquals(mData[5], result[5]);
    }

    @Test
    public void bubbleSort() throws Exception {
        long start = System.currentTimeMillis();
        Sort.bubbleSort(mData, false);
        long end = System.currentTimeMillis();
        System.out.print("bubbleSort:" + (end - start) + "\n");

        int[] result = new int[] {1,2,3,4,5,6,7,8,9};
        assertEquals(mData[5], result[5]);
    }

    @Test
    public void shellSort() throws Exception {
        long start = System.currentTimeMillis();
        Sort.shellSort(mData, false);
        long end = System.currentTimeMillis();
        System.out.print("shellSort:" + (end - start) + "\n");

        int[] result = new int[] {1,2,3,4,5,6,7,8,9};
        assertEquals(mData[5], result[5]);
    }

    @Test
    public void quickSort() throws Exception {
        long start = System.currentTimeMillis();
        Sort.quickSort(mData, false);
        long end = System.currentTimeMillis();
        System.out.print("quickSort:" + (end - start) + "\n");

        int[] result = new int[] {1,2,3,4,5,6,7,8,9};
        assertEquals(mData[5], result[5]);
    }

    @Test
    public void heapSort() throws Exception {
        long start = System.currentTimeMillis();
        Sort.heapSort(mData, false);
        long end = System.currentTimeMillis();
        System.out.print("heapSort:" + (end - start) + "\n");

        int[] result = new int[] {1,2,3,4,5,6,7,8,9};
        assertEquals(mData[5], result[5]);
    }

    @Test
    public void mergeSort() throws Exception {
        long start = System.currentTimeMillis();
        Sort.mergeSort(mData, false);
        long end = System.currentTimeMillis();
        System.out.print("mergeSort:" + (end - start) + "\n");

        int[] result = new int[] {1,2,3,4,5,6,7,8,9};
        assertEquals(mData[5], result[5]);
    }

    @Test
    public void radixSort() throws Exception {
        long start = System.currentTimeMillis();
        Sort.radixSort(mData, false);
        long end = System.currentTimeMillis();
        System.out.print("radixSort:" + (end - start) + "\n");

        int[] result = new int[] {1,2,3,4,5,6,7,8,9};
        assertEquals(mData[5], result[5]);
    }
}