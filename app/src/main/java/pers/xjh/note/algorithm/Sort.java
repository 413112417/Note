package pers.xjh.note.algorithm;

/**
 * 排序算法
 * Created by XJH on 2017/5/2.
 */

public class Sort {

    /**
     * 选择排序
     *
     * 思路：
     * 首先，找到数组中最小的元素，其次将它和数组中的第一个元素交换位置。
     * 再次，在剩下的数组中找到最小的元素，将它与数组中的第二个元素交换位置。
     * 如此往复，直到整个数组排序
     *
     * 特点：
     * 1.运行时间和输入无关。
     *   一个有序的数组和一个完全无序的数组排序时间一样长。这种性质在某些情况下是缺点，
     *   其它算法更善于利用输入的初始状态。
     * 2.数据移动是最少的。
     *   每次交换都会改变两个数组元素的值，因此排序用了N次交换。交换次数和数组大小是线性关系。
     *   其它的任何算法都不具备这个特征（大部分的增长数量级都是线性对数，或是平方级别）。
     *
     * N(N-1)/2 次比较
     * N 次交换。
     *
     * 时间复杂度:
     * 最差：O(n²)
     * 平均：O(n²)
     *
     * 空间复杂度:
     * O(1)
     *
     * @param data 待排序的数组
     * @param order false 正序 true 倒序
     */
    public static void selectSort(int[] data, boolean order) {
        if (!order) {
            for (int i = 0; i < data.length; i++) {
                int min = i; //先假设i最小
                for (int j = i + 1; j < data.length; j++) {
                    //每次都和最小的数比较，如果有更小的将此数作为比较基准
                    if (data[j] < data[min]) min = j;
                }
                //完成一次扫描后，得到最小数下标，将其和对应的位置交换
                int temp = data[min];
                data[min] = data[i];
                data[i] = temp;
            }
        } else {
            for (int i = 0; i < data.length; i++) {
                int min = i;
                for (int j = i + 1; j < data.length; j++) {
                    if (data[j] > data[min]) min = j;
                }
                int temp = data[min];
                data[min] = data[i];
                data[i] = temp;
            }
        }
    }

    /**
     * 直接插入排序
     *
     * 思路：
     * 首先只看数组中的第一个数，因为只有一个数，所以它是有序的。
     * 然后，用第二个数和第一个数比较，如果比第一个小，则将第一个数后移。否则不动。这样前两位就是有序的。
     * 接着，之后的每一个数，都和前面的有序数组比较，将有序数组中比这个数大的都后移一位，直到找到比这个数小的。
     * 将这个数填入比这个数小的数的后面。（若没有比这个数小的数，则放在第一个位置）
     * 如此往复，直到整个数组排序。（可以类比打扑克时候的理牌过程）
     *
     * 特点：
     *   所需时间取决于输入中元素的初始顺序。（对于已经有序，或者接近有序的数组排序，要比随机顺序，或者逆序数组进行排序要快得多）
     *
     * 最坏：N²/2 次比较 N²/2 次交换
     * 最好：N-1 次比较 0 次交换
     * 平均：N²/4 次比较 N²/4 次交换
     *
     * 时间复杂度:
     * 最差：O(n²)
     * 平均：O(n²)
     *
     * 空间复杂度:
     * O(1)
     *
     * @param data 待排序的数组
     * @param order false 正序 true 倒序
     */
    public static void insertSort(int[] data, boolean order) {
        if(!order) {
            for (int i = 1; i < data.length; i++) {
                //先保存data[i]，不然后移操作会覆盖掉data[i]
                int k = data[i];
                int j = i - 1;
                //将比k大的数都后移一个位置
                while (j > -1 && k < data[j]) {
                    data[j + 1] = data[j];
                    j--;
                }
                //找到比k小的数后，将该数的后一位设为k
                data[j + 1] = k;
            }
        } else {
            for (int i = 1; i < data.length; i++) {
                int k = data[i];
                int j = i - 1;
                while (j > -1 && k > data[j]) {
                    data[j + 1] = data[j];
                    j--;
                }
                data[j + 1] = k;
            }
        }
    }

    /**
     * 冒泡排序
     * @param data 待排序的数组
     * @param order false 正序 true 倒序
     */
    public static void bubbleSort(int[] data, boolean order) {
        if(!order) {
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data.length - i - 1; j++) {
                    //比较相邻的数，将大的数后移
                    if (data[j] > data[j + 1]) {
                        int temp = data[j];
                        data[j] = data[j + 1];
                        data[j + 1] = temp;
                    }
                }
            }
        } else {
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data.length - i - 1; j++) {
                    if (data[j] < data[j + 1]) {
                        int temp = data[j];
                        data[j] = data[j + 1];
                        data[j + 1] = temp;
                    }
                }
            }
        }
    }

    /**
     * 希尔排序
     *
     * 思路:
     * 先将整个待排元素序列分割成若干个子序列（由相隔某个“增量”的元素组成的）
     * 分别进行直接插入排序，然后依次缩减增量再进行排序，待整个序列中的元素基本有序（增量足够小）时，
     * 再对全体元素进行一次直接插入排序。因为直接插入排序在元素基本有序的情况下（接近最好情况），效率是很高的，
     * 因此希尔排序在时间效率上有较大提高。
     *
     *
     * @param data 待排序的数组
     * @param order false 正序 true 倒序
     */
    public static void shellSort(int[] data, boolean order) {
        if(!order) {
            int gap = data.length / 2;
            while (1 <= gap) {
                // 把距离为 gap 的元素编为一个组，扫描所有组
                for (int i = gap; i < data.length; i++) {
                    int j;
                    int temp = data[i];
                    // 对距离为 gap 的元素组进行排序
                    for (j = i - gap; j >= 0 && temp < data[j]; j = j - gap) {
                        data[j + gap] = data[j];
                    }
                    data[j + gap] = temp;
                }
                gap = gap / 2; // 减小增量
            }
        } else {
            int gap = data.length / 2;
            while (1 <= gap) {
                for (int i = gap; i < data.length; i++) {
                    int j;
                    int temp = data[i];
                    for (j = i - gap; j >= 0 && temp > data[j]; j = j - gap) {
                        data[j + gap] = data[j];
                    }
                    data[j + gap] = temp;
                }
                gap = gap / 2;
            }
        }
    }

    /**
     * 快速排序
     * @param data 待排序数组
     */
    public static void quickSort(int[] data, boolean order) {
        //查看数组是否为空
        if(data.length > 0) {
            if(!order)
                quickSortAsc(data, 0, data.length-1);
            else
                quickSortDesc(data, 0, data.length-1);
        }
    }

    private static void quickSortAsc(int[] data,int low,int high){
        int start = low;
        int end = high;
        int key = data[low];

        while(end>start){
            //从后往前比较
            while(end>start && data[end]>=key)  //如果没有比关键值小的，比较下一个，直到有比关键值小的交换位置，然后又从前往后比较
                end--;
            if(data[end]<key){
                int temp = data[end];
                data[end] = data[start];
                data[start] = temp;
            }
            //从前往后比较
            while(end>start && data[start]<=key)//如果没有比关键值大的，比较下一个，直到有比关键值大的交换位置
                start++;
            if(data[start]>key){
                int temp = data[start];
                data[start] = data[end];
                data[end] = temp;
            }
            //此时第一次循环比较结束，关键值的位置已经确定了。左边的值都比关键值小，右边的值都比关键值大，但是两边的顺序还有可能是不一样的，进行下面的递归调用
        }
        //递归
        if(start>low) quickSortAsc(data,low,start-1);//左边序列。第一个索引位置到关键值索引-1
        if(end<high) quickSortAsc(data,end+1,high);//右边序列。从关键值索引+1到最后一个
    }

    private static void quickSortDesc(int[] data,int low,int high){
        int start = low;
        int end = high;
        int key = data[low];

        while(end>start){
            //从后往前比较
            while(end>start && data[end]<=key)  //如果没有比关键值小的，比较下一个，直到有比关键值小的交换位置，然后又从前往后比较
                end--;
            if(data[end]>key){
                int temp = data[end];
                data[end] = data[start];
                data[start] = temp;
            }
            //从前往后比较
            while(end>start && data[start]>=key)//如果没有比关键值大的，比较下一个，直到有比关键值大的交换位置
                start++;
            if(data[start]<key){
                int temp = data[start];
                data[start] = data[end];
                data[end] = temp;
            }
            //此时第一次循环比较结束，关键值的位置已经确定了。左边的值都比关键值小，右边的值都比关键值大，但是两边的顺序还有可能是不一样的，进行下面的递归调用
        }
        //递归
        if(start>low) quickSortDesc(data,low,start-1);//左边序列。第一个索引位置到关键值索引-1
        if(end<high) quickSortDesc(data,end+1,high);//右边序列。从关键值索引+1到最后一个
    }

    /**
     * 堆排序
     * @param array
     */
    public static void heapSort(int[] array, boolean order) {
        if (array == null || array.length <= 1) {
            return;
        }

        buildHeap(array, order);

        for (int i = array.length - 1; i >= 1; i--) {
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;
            if(!order) maxHeap(array, i, 0);
            else minHeap(array, i, 0);
        }
    }

    private static void buildHeap(int[] array, boolean order) {
        if (array == null || array.length <= 1) {
            return;
        }

        int half = array.length / 2;

        if(!order) {
            for (int i = half; i >= 0; i--) {
                maxHeap(array, array.length, i);
            }
        } else {
            for (int i = half; i >= 0; i--) {
                minHeap(array, array.length, i);
            }
        }
    }

    private static void maxHeap(int[] array, int heapSize, int index) {
        int left = index * 2 + 1;
        int right = index * 2 + 2;

        int largest = index;
        if (left < heapSize && array[left] > array[index]) {
            largest = left;
        }

        if (right < heapSize && array[right] > array[largest]) {
            largest = right;
        }

        if (index != largest) {
            int temp = array[index];
            array[index] = array[largest];
            array[largest] = temp;
            maxHeap(array, heapSize, largest);
        }
    }

    private static void minHeap(int[] array, int heapSize, int index) {
        int left = index * 2 + 1;
        int right = index * 2 + 2;

        int largest = index;
        if (left < heapSize && array[left] < array[index]) {
            largest = left;
        }

        if (right < heapSize && array[right] < array[largest]) {
            largest = right;
        }

        if (index != largest) {
            int temp = array[index];
            array[index] = array[largest];
            array[largest] = temp;
            minHeap(array, heapSize, largest);
        }
    }

    /**
     * 归并排序
     * 思路:
     * 将两个（或两个以上）有序表合并成一个新的有序表 即把待排序序列分为若干个子序列，每个子序列是有序的。然后再把有序子序列合并为整体有序序列
     *
     * 时间复杂度为O(nlogn)
     *
     * 最好NlgN/2 次比较
     * 最坏NlgN 次比较
     *
     * 稳定排序方式
     * @param data 待排序数组
     * @return 输出有序数组
     */
    public static void mergeSort(int[] data, boolean order) {
        if(data.length > 0) {
            if(!order)
                mergeSortAsc(data, 0, data.length-1);
            else
                mergeSortDesc(data, 0, data.length-1);
        }
    }

    private static void mergeSortAsc(int[] data, int low, int high) {
        int mid = (low + high) / 2;
        if (low < high) {
            // 左边
            mergeSortAsc(data, low, mid);
            // 右边
            mergeSortAsc(data, mid + 1, high);
            // 左右归并
            mergeAsc(data, low, mid, high);
        }
    }

    private static void mergeSortDesc(int[] data, int low, int high) {
        int mid = (low + high) / 2;
        if (low < high) {
            // 左边
            mergeSortDesc(data, low, mid);
            // 右边
            mergeSortDesc(data, mid + 1, high);
            // 左右归并
            mergeDesc(data, low, mid, high);
        }
    }

    private static void mergeAsc(int[] data, int low, int mid, int high) {
        int[] temp = new int[high - low + 1];
        int i = low;// 左指针
        int j = mid + 1;// 右指针
        int k = 0;

        // 把较小的数先移到新数组中
        while (i <= mid && j <= high) {
            if (data[i] < data[j]) {
                temp[k++] = data[i++];
            } else {
                temp[k++] = data[j++];
            }
        }

        // 把左边剩余的数移入数组
        while (i <= mid) {
            temp[k++] = data[i++];
        }

        // 把右边边剩余的数移入数组
        while (j <= high) {
            temp[k++] = data[j++];
        }

        // 把新数组中的数覆盖data数组
        for (int k2 = 0; k2 < temp.length; k2++) {
            data[k2 + low] = temp[k2];
        }
    }

    private static void mergeDesc(int[] data, int low, int mid, int high) {
        int[] temp = new int[high - low + 1];
        int i = low;// 左指针
        int j = mid + 1;// 右指针
        int k = 0;

        // 把较小的数先移到新数组中
        while (i <= mid && j <= high) {
            if (data[i] > data[j]) {
                temp[k++] = data[i++];
            } else {
                temp[k++] = data[j++];
            }
        }

        // 把左边剩余的数移入数组
        while (i <= mid) {
            temp[k++] = data[i++];
        }

        // 把右边边剩余的数移入数组
        while (j <= high) {
            temp[k++] = data[j++];
        }

        // 把新数组中的数覆盖data数组
        for (int k2 = 0; k2 < temp.length; k2++) {
            data[k2 + low] = temp[k2];
        }
    }

    /**
     * 基数排序
     * @param data
     */
    public static void radixSort(int[] data, boolean order) {
        if(data.length > 0) {
            if(!order)
                radixSortAsc(data, getMaxWeishu(data));
            else
                radixSortDesc(data, getMinWeishu(data));
        }
    }

    private static void radixSortAsc(int[] data, int d) {

        int[][] array = new int[10][data.length + 1];
        for (int i = 0; i < 10; i++) {
            array[i][0] = 0;// array[i][0]记录第i行数据的个数
        }
        for (int pos = 1; pos <= d; pos++) {
            for (int i = 0; i < data.length; i++) {// 分配过程
                int row = getNumInPos(data[i], pos);
                int col = ++array[row][0];
                array[row][col] = data[i];
            }
            for (int row = 0, i = 0; row < 10; row++) {// 收集过程
                for (int col = 1; col <= array[row][0]; col++) {
                    data[i++] = array[row][col];
                }
                array[row][0] = 0;// 复位，下一个pos时还需使用
            }
        }
    }

    private static void radixSortDesc(int[] data, int d) {

        int[][] array = new int[10][data.length + 1];
        for (int i = 0; i < 10; i++) {
            array[i][0] = 0;// array[i][0]记录第i行数据的个数
        }
        for (int pos = 1; pos <= d; pos++) {
            for (int i = 0; i < data.length; i++) {// 分配过程
                int row = getNumInPos(data[i], pos);
                int col = ++array[row][0];
                array[row][col] = data[i];
            }
            for (int row = 0, i = 0; row < 10; row++) {// 收集过程
                for (int col = 1; col <= array[row][0]; col++) {
                    data[i++] = array[row][col];
                }
                array[row][0] = 0;// 复位，下一个pos时还需使用
            }
        }
    }

    //pos=1表示个位，pos=2表示十位
    public static int getNumInPos(int num, int pos) {
        int tmp = 1;
        for (int i = 0; i < pos - 1; i++) {
            tmp *= 10;
        }
        return (num / tmp) % 10;
    }

    //求得最大位数d
    public static int getMaxWeishu(int[] data) {
        int max = data[0];
        for (int i = 0; i < data.length; i++) {
            if (data[i] > max)
                max = data[i];
        }
        int tmp = 1, d = 1;
        while (true) {
            tmp *= 10;
            if (max / tmp != 0) {
                d++;
            } else
                break;
        }
        return d;
    }

    //求得最小位数d
    public static int getMinWeishu(int[] data) {
        int min = data[0];
        for (int i = 0; i < data.length; i++) {
            if (data[i] < min)
                min = data[i];
        }
        int tmp = 1, d = 1;
        while (true) {
            tmp *= 10;
            if (min / tmp != 0) {
                d++;
            } else
                break;
        }
        return d;
    }
}
