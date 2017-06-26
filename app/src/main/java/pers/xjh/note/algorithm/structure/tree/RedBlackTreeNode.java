package pers.xjh.note.algorithm.structure.tree;

/**
 * 红黑树节点
 * Created by XJH on 2017/6/3.
 */

public class RedBlackTreeNode {

    public enum Color {
        RED, BLACK
    }

    //节点的值
    private int value;
    //节点的颜色
    private Color color = Color.RED;
    //父节点
    private RedBlackTreeNode parentNode;
    //左子节点
    private RedBlackTreeNode leftChildNode;
    //右子节点
    private RedBlackTreeNode rightChildNode;
    //节点所在的层数
    private int degree;
    //记录节点是否被访问过
    private boolean visited;

    public RedBlackTreeNode() {
    }

    public RedBlackTreeNode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isRed() {
        return color == Color.RED;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public RedBlackTreeNode getParentNode() {
        return parentNode;
    }

    public void setParentNode(RedBlackTreeNode parentNode) {
        this.parentNode = parentNode;
    }

    public RedBlackTreeNode getLeftChildNode() {
        return leftChildNode;
    }

    public void setLeftChildNode(RedBlackTreeNode leftChildNode) {
        this.leftChildNode = leftChildNode;
        if(leftChildNode != null) {
            leftChildNode.setParentNode(this);
        }
    }

    public RedBlackTreeNode getRightChildNode() {
        return rightChildNode;
    }

    public void setRightChildNode(RedBlackTreeNode rightChildNode) {
        this.rightChildNode = rightChildNode;
        if(rightChildNode != null) {
            rightChildNode.setParentNode(this);
        }
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public boolean isVisited() {
        return visited;
    }

    public void visit() {
        visited = true;
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
