package pers.xjh.note.algorithm.structure.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 节点
 * Created by XJH on 2017/5/31.
 */

public class TreeNode {

    //节点的值
    private int value;
    //子节点
    private List<TreeNode> childNodes = new ArrayList<>();
    //节点所在的层数
    private int degree;
    //记录节点是否被访问过
    private boolean visited;

    public TreeNode() {
    }

    public TreeNode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void addChildNode(TreeNode node) {
        if(node != null) {
            node.setDegree(degree + 1);
        }
        childNodes.add(node);
    }

    public List<TreeNode> getChildNodes() {
        return childNodes;
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
