package pers.xjh.note.algorithm.structure.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 红黑树
 * Created by XJH on 2017/6/3.
 */

public class RedBlackTree {

    private List<RedBlackTreeNode> nodes;

    public RedBlackTree() {
        nodes = new ArrayList<>();
    }

    public RedBlackTree(List<RedBlackTreeNode> nodes) {
        this.nodes = nodes;
    }

    public List<RedBlackTreeNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<RedBlackTreeNode> nodes) {
        this.nodes = nodes;
    }

    public RedBlackTreeNode getRootNode() {
        return nodes == null ? null : nodes.get(0);
    }

    public void setRootNode(RedBlackTreeNode node) {
        nodes.set(0, node);
    }

    /**
     * 生成红黑树
     */
    public void buildRedBlackTree(int[] data) {
        nodes.clear();
        if(data != null && data.length > 0) {
            for(int i : data) {
                RedBlackTreeNode node = new RedBlackTreeNode(i);
                insert(node);
            }
        }
    }

    /**
     * 将节点插入红黑树中(同二叉查找树)
     * @param node
     */
    public void insert(RedBlackTreeNode node) {
        //1.将红黑树当作一颗二叉查找树，将节点添加到二叉查找树中
        if(nodes.size() > 0) {
            insert(nodes.get(0), node);
        }
        nodes.add(node);

        //2.设置节点的颜色为红色
        node.setColor(RedBlackTreeNode.Color.RED);

        //3.将它重新修正为一颗二叉查找树
        insertFixUp(node);
    }

    private void insert(RedBlackTreeNode root , RedBlackTreeNode node) {
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
     * 红黑树插入修正函数
     *
     * 在向红黑树中插入节点之后(失去平衡)，再调用该函数；
     * 目的是将它重新塑造成一颗红黑树。
     *
     */
    private void insertFixUp(RedBlackTreeNode node) {
        //父节点和祖父节点
        RedBlackTreeNode parent, grandparent;

        //若父节点存在，并且父节点的颜色是红色
        while (((parent = node.getParentNode()) != null) && parent.isRed()) {
            grandparent = parent.getParentNode();

            //若父节点是祖父节点的左孩子
            if (parent == grandparent.getLeftChildNode()) {
                // Case 1条件：叔叔节点是红色
                RedBlackTreeNode uncle = grandparent.getRightChildNode();
                if ((uncle != null) && uncle.isRed()) {
                    uncle.setColor(RedBlackTreeNode.Color.BLACK);
                    parent.setColor(RedBlackTreeNode.Color.BLACK);
                    grandparent.setColor(RedBlackTreeNode.Color.RED);
                    node = grandparent;
                    continue;
                }

                //Case 2条件：叔叔是黑色，且当前节点是右孩子
                if (parent.getRightChildNode() == node) {
                    RedBlackTreeNode tmp;
                    leftRotate(parent);
                    tmp = parent;
                    parent = node;
                    node = tmp;
                }

                //Case 3条件：叔叔是黑色，且当前节点是左孩子。
                parent.setColor(RedBlackTreeNode.Color.BLACK);
                grandparent.setColor(RedBlackTreeNode.Color.RED);
                rightRotate(grandparent);
            } else {
                //若z的父节点是z的祖父节点的右孩子
                //Case 1条件：叔叔节点是红色
                RedBlackTreeNode uncle = grandparent.getLeftChildNode();
                if ((uncle != null) && uncle.isRed()) {
                    uncle.setColor(RedBlackTreeNode.Color.BLACK);
                    parent.setColor(RedBlackTreeNode.Color.BLACK);
                    grandparent.setColor(RedBlackTreeNode.Color.RED);
                    node = grandparent;
                    continue;
                }

                //Case 2条件：叔叔是黑色，且当前节点是左孩子
                if (parent.getLeftChildNode() == node) {
                    RedBlackTreeNode tmp;
                    rightRotate(parent);
                    tmp = parent;
                    parent = node;
                    node = tmp;
                }

                //Case 3条件：叔叔是黑色，且当前节点是右孩子
                parent.setColor(RedBlackTreeNode.Color.BLACK);
                grandparent.setColor(RedBlackTreeNode.Color.RED);
                leftRotate(grandparent);
            }
        }

        //将根节点设为黑色
        getRootNode().setColor(RedBlackTreeNode.Color.BLACK);

        //设置树的层级信息
        setDegree();
    }

    /**
     * 对红黑树的节点(x)进行左旋转
     *
     * 左旋示意图(对节点x进行左旋)：
     *      px                              px
     *     /                               /
     *    x                               y
     *   /  \      --(左旋)--           / \
     *  lx   y                          x  ry
     *     /   \                       /  \
     *    ly   ry                     lx  ly
     *
     */
    private void leftRotate(RedBlackTreeNode x) {
        //设置x的右孩子为y
        RedBlackTreeNode y = x.getRightChildNode();

        //将y的左孩子设为x的右孩子
        //如果y的左孩子非空，将x设为y的左孩子的父亲
        x.setRightChildNode(y.getLeftChildNode());
        if (y.getLeftChildNode() != null)
            y.getLeftChildNode().setParentNode(x);

        //将 x的父亲设为y的父亲
        y.setParentNode(x.getParentNode());

        if (x.getParentNode() == null) {
            setRootNode(y);            //如果x的父亲是空节点，则将y设为根节点
        } else {
            if (x.getParentNode().getLeftChildNode() == x)
                x.getParentNode().setLeftChildNode(y);    //如果x是它父节点的左孩子，则将y设为x的父节点的左孩子
            else
                x.getParentNode().setRightChildNode(y);    //如果x是它父节点的左孩子，则将y设为x的父节点的左孩子
        }

        //将x设为y的左孩子
        y.setLeftChildNode(x);
        //将x的父节点设为y
        x.setParentNode(y);
    }

    /**
     * 对红黑树的节点(y)进行右旋转
     *
     * 右旋示意图(对节点y进行左旋)：
     *            py                               py
     *           /                                /
     *          y                                x
     *         /  \      --(右旋)--            /  \
     *        x   ry                           lx   y
     *       / \                                   / \
     *      lx  rx                                rx  ry
     *
     */
    private void rightRotate(RedBlackTreeNode y) {
        //设置x是当前节点的左孩子。
        RedBlackTreeNode x = y.getLeftChildNode();

        //将x的右孩子设为 y的左孩子
        //如果x的右孩子不为空的话，将y设为x的右孩子的父亲
        y.setLeftChildNode(x.getRightChildNode());
        if (x.getRightChildNode() != null)
            x.getRightChildNode().setParentNode(y);

        //将y的父亲设为x的父亲
        x.setParentNode(y.getParentNode());

        if (y.getParentNode() == null) {
            setRootNode(x);            //如果y的父亲是空节点，则将x设为根节点
        } else {
            if (y == y.getParentNode().getRightChildNode())
                y.getParentNode().setRightChildNode(x);    //如果y是它父节点的右孩子，则将x设为y的父节点的右孩子
            else
                y.getParentNode().setLeftChildNode(x);    //(y是它父节点的左孩子) 将x设为x的父节点的左孩子
        }

        //将y设为x的右孩子
        x.setRightChildNode(y);

        //将y的父节点设为x
        y.setParentNode(x);
    }

    /**
     * 查找
     * @param data
     * @return
     */
    public RedBlackTreeNode search(int data) {
        if(nodes.size() > 0) {
            return search(getRootNode(), data);
        }
        return null;
    }

    private RedBlackTreeNode search(RedBlackTreeNode node, int data) {
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
     * 设置树节点的层级信息
     */
    private void setDegree() {
        if(nodes != null) {
            RedBlackTreeNode node = getRootNode();
            node.setDegree(0);
            Queue<RedBlackTreeNode> nodeQueue = new LinkedList<>();
            nodeQueue.offer(node);
            while(!nodeQueue.isEmpty()){
                node = nodeQueue.poll();
                RedBlackTreeNode leftChildNode = node.getLeftChildNode();
                if (leftChildNode != null){
                    leftChildNode.setDegree(node.getDegree() + 1);
                    nodeQueue.offer(leftChildNode);
                }
                RedBlackTreeNode rightChildNode = node.getRightChildNode();
                if (rightChildNode != null){
                    rightChildNode.setDegree(node.getDegree() + 1);
                    nodeQueue.offer(rightChildNode);
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
            RedBlackTreeNode node = getRootNode();
            Queue<RedBlackTreeNode> nodeQueue = new LinkedList<>();
            nodeQueue.offer(node);
            while(!nodeQueue.isEmpty()){
                node = nodeQueue.poll();
                if(node.getDegree() > maxDegree) {
                    maxDegree = node.getDegree();
                }
                RedBlackTreeNode leftChildNode = node.getLeftChildNode();
                if (leftChildNode != null){
                    nodeQueue.offer(leftChildNode);
                }
                RedBlackTreeNode rightChildNode = node.getRightChildNode();
                if (rightChildNode != null){
                    nodeQueue.offer(rightChildNode);
                }
            }
        }
        return maxDegree;
    }
}
