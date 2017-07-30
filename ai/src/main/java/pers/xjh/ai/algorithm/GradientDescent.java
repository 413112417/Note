package pers.xjh.ai.algorithm;

import pers.xjh.ai.algorithm.structure.Point;

/**
 * 梯度下降
 * Created by xjh on 17-7-30.
 */

public class GradientDescent {

    /**
     * 批量梯度下降
     */
    public static void batchGradientDescent(Point[] targets, float[] weights) {
        for(int i=0; i<weights.length; i++) {
            for(int j=0; j<targets.length; j++) {
                weights[i] += (targets[j].getY() - (weights[0] + weights[1] * targets[j].getX())) * targets[j].getX() * 0.3f;
            }
        }
    }
}
