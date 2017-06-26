package pers.xjh.note.algorithm.structure.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

/**
 * 树
 * Created by XJH on 2017/5/31.
 */

public class Tree {

    protected List<TreeNode> nodes;

    private static Random random = new Random();

    public Tree() {
        nodes = new ArrayList<>();
    }

    public Tree(List<TreeNode> nodes) {
        this.nodes = nodes;
    }

    public List<TreeNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<TreeNode> nodes) {
        this.nodes = nodes;
    }

    public TreeNode getRootNode() {
        return nodes == null ? null : nodes.get(0);
    }

    /**
     * 生成随机树
     * @param data
     */
    public void buildRandomTree(int[] data) {
        if(data != null && data.length > 0) {
            nodes.clear();
            for(int i=0; i<data.length; i++) {
                nodes.add(new TreeNode(data[i]));
                if(i > 0) {
                    int index = random.nextInt(i);
                    TreeNode parentNode = nodes.get(index);
                    TreeNode childNode = nodes.get(i);
                    parentNode.addChildNode(childNode);
                }
            }
        }
    }

    /**
     * 生成完全二叉树
     * @param data
     */
    public void buildCompleteBinaryTree(int[] data) {
        if(data != null && data.length > 0) {
            nodes.clear();
            for(int i=0; i<data.length; i++) {
                nodes.add(new TreeNode(data[i]));
                if(i > 0) {
                    TreeNode parentNode = nodes.get((i-1) / 2);
                    TreeNode childNode = nodes.get(i);
                    parentNode.addChildNode(childNode);
                }
            }
        }
    }

    /**
     * 生成最大堆
     * @param data
     */
    public void buildMaxHeap(int[] data) {
        if(data != null && data.length > 0) {
            nodes.clear();
            List<TreeNode> nodes = new ArrayList<>();
            for(int i : data) {
                MaxHeap.insert(nodes, new TreeNode(i));
            }
            setNodes(nodes);
            for(int i=0; i<nodes.size(); i++) {
                if(i > 0) {
                    TreeNode parentNode = nodes.get((i-1) / 2);
                    TreeNode childNode = nodes.get(i);
                    parentNode.addChildNode(childNode);
                }
            }
        }
    }

    /**
     * 生成最小堆
     * @param data
     */
    public void buildMinHeap(int[] data) {
        if(data != null && data.length > 0) {
            nodes.clear();
            List<TreeNode> nodes = new ArrayList<>();
            for(int i : data) {
                MinHeap.insert(nodes, new TreeNode(i));
            }
            setNodes(nodes);
            for(int i=0; i<nodes.size(); i++) {
                if(i > 0) {
                    TreeNode parentNode = nodes.get((i-1) / 2);
                    TreeNode childNode = nodes.get(i);
                    parentNode.addChildNode(childNode);
                }
            }
        }
    }

    /**
     * 获得树的最大层数
     * @return
     */
    public int getMaxDegree() {
        int maxDegree = 0;
        if(nodes != null) {
            TreeNode node = getRootNode();
            Queue<TreeNode> nodeQueue = new LinkedList<>();
            nodeQueue.offer(node);
            while(!nodeQueue.isEmpty()){
                node = nodeQueue.poll();
                if(node.getDegree() > maxDegree) {
                    maxDegree = node.getDegree();
                }
                List<TreeNode> childNodes = node.getChildNodes();
                if (childNodes != null && childNodes.size() > 0){
                    for(TreeNode childNode : childNodes) {
                        nodeQueue.offer(childNode);
                    }
                }
            }
        }
        return maxDegree;
    }
}
