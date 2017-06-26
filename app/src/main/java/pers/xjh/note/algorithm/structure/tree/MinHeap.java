package pers.xjh.note.algorithm.structure.tree;

import java.util.List;

import static java.util.Collections.swap;

/**
 * 最小堆
 * Created by XJH on 2017/6/2.
 */

public class MinHeap {

    //向最大堆中插入元素, heap:存放堆元素的数组
    public static void insert(List<TreeNode> heap, TreeNode node) {
        //在数组的尾部添加
        heap.add(node);
        //开始上升操作
        heapUp(heap, heap.size() - 1);
    }

    //上升，让插入的数和父节点的数值比较，当大于父节点的时候就和父节点的值相交换
    private static void heapUp(List<TreeNode> heap, int index) {

        if (index > 0) {
            //求出父亲的节点
            int parent = (index-1) / 2;

            //如果父亲节点比index的数值大，就交换二者的数值
            if (heap.get(parent).getValue() > heap.get(index).getValue()) {
                //交换数值
                swap(heap, parent, index);
                //递归调用
                heapUp(heap, parent);
            }
        }
    }

    /**
     * 删除堆中位置是index处的节点
     * 操作原理是：当删除节点的数值时，原来的位置就会出现一个孔
     * 填充这个孔的方法就是，把最后的叶子的值赋给该孔，最后把该叶子删除
     * @param heap
     */
    public static void delete(List<TreeNode> heap,int index) {
        //把最后的一个叶子的数值赋值给index位置
        heap.set(index, heap.get(heap.size() - 1));
        //下沉操作
        heapDown(heap, index);
        //把最后一个位置的数字删除
        heap.remove(heap.size() - 1);
    }

    /**
     * 递归实现
     * 删除堆中一个数据的时候，根据堆的性质，应该把相应的位置下移，才能保持住堆性质不变
     * @param heap 保持堆元素的数组
     * @param index 被删除的那个节点的位置
     */
    public static void heapDown(List<TreeNode> heap, int index) {

        int n = heap.size() - 1;

        //记录最大的那个儿子节点的位置
        int child = -1;

        //说明该节点没有左右儿子节点了，那么就返回
        if (2 * (index+1) - 1 > n) {
            return;
        } //如果左右儿子都存在
        else if (2 * (index+1) - 1 < n) {

            //定义左儿子节点
            child = 2 * (index+1) - 1;
            //如果左儿子小于右儿子的数值，取右儿子的下标
            if (heap.get(child).getValue() > heap.get(child + 1).getValue()) {
                child++;
            }

        }//如果只有一个儿子（左儿子节点）
        else if (2 * (index+1) - 1 == n) {
            child = 2 * (index+1) - 1;
        }

        if (heap.get(child).getValue() < heap.get(index).getValue()) {
            //交换堆中的child，和index位置的值
            swap(heap, child, index);
            //完成交换后递归调用，继续下降
            heapDown(heap, child);
        }
    }
}
