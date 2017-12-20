package pers.xjh.note.algorithm;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

/**
 * Created by xjh on 17-12-20.
 */
public class SortTest {

    Random mRandom;

    int[] mData;

    @Before
    public void setUp() throws Exception {
        mRandom = new Random();
        mData = new int[100000];
        for(int i=0; i<mData.length; i++) {
            mData[i] = mRandom.nextInt();
        }
    }

    @Test
    public void selectSort() throws Exception {
        long start = System.currentTimeMillis();
        Sort.selectSort(mData, false);
        long end = System.currentTimeMillis();
        System.out.print("selectSort:" + (end - start) + "\n");
    }

    @Test
    public void insertSort() throws Exception {
        long start = System.currentTimeMillis();
        Sort.insertSort(mData, false);
        long end = System.currentTimeMillis();
        System.out.print("insertSort:" + (end - start) + "\n");
    }

    @Test
    public void bubbleSort() throws Exception {
        long start = System.currentTimeMillis();
        Sort.bubbleSort(mData, false);
        long end = System.currentTimeMillis();
        System.out.print("bubbleSort:" + (end - start) + "\n");
    }

    @Test
    public void shellSort() throws Exception {
        long start = System.currentTimeMillis();
        Sort.shellSort(mData, false);
        long end = System.currentTimeMillis();
        System.out.print("shellSort:" + (end - start) + "\n");
    }

    @Test
    public void quickSort() throws Exception {
        long start = System.currentTimeMillis();
        Sort.quickSort(mData, false);
        long end = System.currentTimeMillis();
        System.out.print("quickSort:" + (end - start) + "\n");
    }

    @Test
    public void heapSort() throws Exception {
        long start = System.currentTimeMillis();
        Sort.heapSort(mData, false);
        long end = System.currentTimeMillis();
        System.out.print("heapSort:" + (end - start) + "\n");
    }

    @Test
    public void mergeSort() throws Exception {
        long start = System.currentTimeMillis();
        Sort.mergeSort(mData, false);
        long end = System.currentTimeMillis();
        System.out.print("mergeSort:" + (end - start) + "\n");
    }

    @Test
    public void radixSort() throws Exception {
        long start = System.currentTimeMillis();
        Sort.radixSort(mData, false);
        long end = System.currentTimeMillis();
        System.out.print("radixSort:" + (end - start) + "\n");
    }
}