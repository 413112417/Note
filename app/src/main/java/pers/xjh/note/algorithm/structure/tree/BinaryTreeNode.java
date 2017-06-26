package pers.xjh.note.algorithm.structure.tree;

/**
 * 二叉树节点
 * Created by XJH on 2017/5/31.
 */

public class BinaryTreeNode {

    //节点的值
    private int value;
    //父节点
    private BinaryTreeNode parentNode;
    //左子节点
    private BinaryTreeNode leftChildNode;
    //右子节点
    private BinaryTreeNode rightChildNode;
    //节点所在的层数
    private int degree;
    //记录节点是否被访问过
    private boolean visited;

    public BinaryTreeNode() {
    }

    public BinaryTreeNode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public BinaryTreeNode getParentNode() {
        return parentNode;
    }

    public void setParentNode(BinaryTreeNode parentNode) {
        if(parentNode != null) {
            degree = parentNode.getDegree() + 1;
        }
        this.parentNode = parentNode;
    }

    public BinaryTreeNode getLeftChildNode() {
        return leftChildNode;
    }

    public void setLeftChildNode(BinaryTreeNode leftChildNode) {
        if(leftChildNode != null) {
            leftChildNode.setDegree(degree + 1);
            leftChildNode.setParentNode(this);
        }
        this.leftChildNode = leftChildNode;
    }

    public BinaryTreeNode getRightChildNode() {
        return rightChildNode;
    }

    public void setRightChildNode(BinaryTreeNode rightChildNode) {
        if(rightChildNode != null) {
            rightChildNode.setDegree(degree + 1);
            rightChildNode.setParentNode(this);
        }
        this.rightChildNode = rightChildNode;
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
