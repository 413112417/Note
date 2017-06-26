package pers.xjh.note.utils;

/**
 * Created by XJH on 2017/6/3.
 */

public class MathUtil {

    /**
     * 找到数组最大值
     * @return
     */
    public static int getMaxValue(int[] data) {
        int max = -1;
        if(data != null && data.length > 0) {
            for (int i : data) {
                if(i > max)
                    max = i;
            }
        }
        return max;
    }
}
