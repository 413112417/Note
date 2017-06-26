package pers.xjh.note.algorithm;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import pers.xjh.note.algorithm.structure.graph.Graph;
import pers.xjh.note.algorithm.structure.graph.Point;
import pers.xjh.note.algorithm.structure.tree.TreeNode;
import pers.xjh.note.algorithm.structure.tree.Tree;

/**
 * 查找算法
 * Created by XJH on 2017/5/16.
 */

public class Search {

    /**
     * 树
     * 深度优先搜索(递归实现,左序遍历)
     * @param tree
     */
    public static void depthFirstSearch(Tree tree) {
        if(tree != null && tree.getNodes() != null && tree.getNodes().size() > 0) {
            depthFirstSearch(tree.getRootNode());
        } else {
            throw new RuntimeException("树为空");
        }
    }

    private static void depthFirstSearch(TreeNode node) {
        if(node != null) {
            node.visit();
            List<TreeNode> childNodes = node.getChildNodes();
            if (childNodes != null && childNodes.size() > 0) {
                for (TreeNode childNode : childNodes) {
                    depthFirstSearch(childNode);
                }
            }
        }
    }

    /**
     * 树
     * 深度优先搜索(堆栈实现,右序遍历)
     * @param tree
     */
    public static void depthFirstSearch2(Tree tree) {
        if(tree == null || tree.getNodes() == null || tree.getNodes().size() <= 0) {
            throw new RuntimeException("树为空");
        }

        TreeNode node = tree.getRootNode();

        Stack<TreeNode> nodeStack = new Stack<>();
        nodeStack.push(node);

        while (!nodeStack.isEmpty()) {
            node = nodeStack.pop();
            node.visit();
            List<TreeNode> childNodes = node.getChildNodes();
            if (childNodes != null && childNodes.size() > 0){
                for(TreeNode childNode : childNodes) {
                    nodeStack.push(childNode);
                }
            }
        }
    }

    /**
     * 树
     * 广度优先搜索(队列实现)
     * @param tree
     */
    public static void broadFirstSearch(Tree tree) {
        if(tree == null || tree.getNodes() == null || tree.getNodes().size() <= 0) {
            throw new RuntimeException("树为空");
        }

        TreeNode node = tree.getRootNode();

        Queue<TreeNode> nodeQueue = new LinkedList<>();
        nodeQueue.offer(node);
        while(!nodeQueue.isEmpty()){
            node = nodeQueue.poll();
            node.visit();
            List<TreeNode> childNodes = node.getChildNodes();
            if (childNodes != null && childNodes.size() > 0){
                for(TreeNode childNode : childNodes) {
                    nodeQueue.offer(childNode);
                }
            }
        }
    }

    /**
     * 图
     * 深度优先搜索(递归实现)
     * @param graph
     */
    public static void depthFirstSearch(Graph graph) {
        if(graph != null && graph.getPoints() != null && graph.getPoints().size() > 0) {
            depthFirstSearch(graph.getPoints().get(0));
        } else {
            throw new RuntimeException("图为空");
        }
    }

    public static void depthFirstSearch(Point point) {
        if(point != null) {
            if(!point.isVisited()) {
                point.visit();
                Set<Point> neighborPoints = point.getNeighborPoints();
                for (Point neighborPoint : neighborPoints) {
                    depthFirstSearch(neighborPoint);
                }
            } else {
                return;
            }
        }
    }

    /**
     * 图
     * 广度优先搜索(队列实现)
     * @param graph
     */
    public static void broadFirstSearch(Graph graph) {
        if(graph == null || graph.getPoints() == null || graph.getPoints().size() <= 0) {
            throw new RuntimeException("图为空");
        }

        Point point = graph.getPoints().get(0);
        point.visit();
        Queue<Point> pointQueue = new LinkedList<>();
        pointQueue.offer(point);
        while(!pointQueue.isEmpty()){
            point = pointQueue.poll();
            Set<Point> neighborPoints = point.getNeighborPoints();
            if (neighborPoints != null && neighborPoints.size() > 0) {
                for (Point neighborPoint : neighborPoints) {
                    if(!neighborPoint.isVisited()) {
                        neighborPoint.visit();
                        pointQueue.offer(neighborPoint);
                    }
                }
            }
        }
    }

    /**
     * 最短路径
     * @param startPoint 起始点
     * @param endPoint 结束点
     */
    public static int shortestPath(Point startPoint, Point endPoint) {
        int shortestPath = 0;
        if(startPoint != endPoint) {
            Point point = startPoint;
            point.visit();
            Queue<Point> pointQueue = new LinkedList<>();
            pointQueue.offer(point);
            while (!pointQueue.isEmpty()) {
                point = pointQueue.poll();
                Set<Point> neighborPoints = point.getNeighborPoints();
                shortestPath++;
                if (neighborPoints != null && neighborPoints.size() > 0) {
                    for (Point neighborPoint : neighborPoints) {
                        if (!neighborPoint.isVisited()) {
                            neighborPoint.visit();
                            if(neighborPoint == endPoint) {
                                return shortestPath;
                            }
                            pointQueue.offer(neighborPoint);
                        }
                    }
                }
            }
        }
        return shortestPath;
    }
}
