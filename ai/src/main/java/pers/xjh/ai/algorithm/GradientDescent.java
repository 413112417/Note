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
    public static void batchGradientDescent(Point[] targets, float[] weights, float descender) {
        batchGradientDescent(targets, weights, -1, descender);
    }

    /**
     * 批量梯度下降
     */
    private static void batchGradientDescent(Point[] targets, float[] weights, float diff, float descender) {

        float newDiff = 0;
        for(int j=0; j<targets.length; j++) {
            newDiff += (targets[j].getY() - (weights[0] + weights[1] * targets[j].getX())) * (targets[j].getY() - (weights[0] + weights[1] * targets[j].getX()));
        }

        if(newDiff > diff && diff != -1) {
            return;
        } else {
            for(int i=0; i<weights.length; i++) {
                for(int j=0; j<targets.length; j++) {
                    weights[i] += (targets[j].getY() - (weights[0] + weights[1] * targets[j].getX())) * targets[j].getX() * descender;
                }
            }
            batchGradientDescent(targets, weights, newDiff, descender);
        }
    }
}
