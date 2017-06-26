package pers.xjh.note.algorithm.structure.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 二叉树
 * Created by XJH on 2017/6/2.
 */

public class BinaryTree {

    private List<BinaryTreeNode> nodes;

    public BinaryTree() {
        nodes = new ArrayList<>();
    }

    public BinaryTree(List<BinaryTreeNode> nodes) {
        this.nodes = nodes;
    }

    public List<BinaryTreeNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<BinaryTreeNode> nodes) {
        this.nodes = nodes;
    }

    public BinaryTreeNode getRootNode() {
        return nodes == null ? null : nodes.get(0);
    }

    /**
     * 生成二叉查找树
     */
    public void buildBinarySearchTree(int[] data) {
        nodes.clear();
        if(data != null && data.length > 0) {
            for(int i : data) {
                BinaryTreeNode node = new BinaryTreeNode(i);
                insert(node);
            }
        }
    }

    /**
     * 将节点插入树中
     * @param node
     */
    public void insert(BinaryTreeNode node) {
        if(nodes.size() > 0) {
            insert(getRootNode(), node);
        }
        nodes.add(node);
    }

    private void insert(BinaryTreeNode root , BinaryTreeNode node) {
        if(node.getValue() < root.getValue()) {
            if(root.getLeftChildNode() != null) {
                insert(root.getLeftChildNode(), node);
            } else {
                root.setLeftChildNode(node);
            }
        } else if(node.getValue() > root.getValue()) {
            if(root.getRightChildNode() != null) {
                insert(root.getRightChildNode(), node);
            } else {
                root.setRightChildNode(node);
            }
        } else {
            return;
        }
    }

    /**
     * 找到对应的值
     * @param data
     */
    public BinaryTreeNode search(int data) {
        if(nodes.size() > 0) {
            return search(getRootNode(), data);
        }
        return null;
    }

    private BinaryTreeNode search(BinaryTreeNode node, int data) {
        if(node != null) {
            node.visit();
            if (data < node.getValue()) {
                return search(node.getLeftChildNode(), data);
            } else if (data > node.getValue()) {
                return search(node.getRightChildNode(), data);
            } else {
                return node;
            }
        } else {
            return null;
        }
    }

    /**
     * 获得树的最大层数
     * @return
     */
    public int getMaxDegree() {
        int maxDegree = 0;
        if(nodes != null) {
            BinaryTreeNode node = getRootNode();
            Queue<BinaryTreeNode> nodeQueue = new LinkedList<>();
            nodeQueue.offer(node);
            while(!nodeQueue.isEmpty()){
                node = nodeQueue.poll();
                if(node.getDegree() > maxDegree) {
                    maxDegree = node.getDegree();
                }
                BinaryTreeNode leftChildNode = node.getLeftChildNode();
                if (leftChildNode != null){
                    nodeQueue.offer(leftChildNode);
                }
                BinaryTreeNode rightChildNode = node.getRightChildNode();
                if (rightChildNode != null){
                    nodeQueue.offer(rightChildNode);
                }
            }
        }
        return maxDegree;
    }
}
