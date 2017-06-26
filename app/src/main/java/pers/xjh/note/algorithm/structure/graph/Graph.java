package pers.xjh.note.algorithm.structure.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by XJH on 2017/6/8.
 */

public class Graph {

    private List<Point> points;

    private Random random = new Random();

    public Graph() {
        points = new ArrayList<>();
    }

    public Graph(List<Point> points) {
        this.points = points;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    /**
     * 生成图
     * @param pointCount 顶点个数
     * @param maxEdgeCount 每个顶点的最大边数
     */
    public void buildGraph(int pointCount, int maxEdgeCount,int width, int height) {
        if(pointCount > 0) {
            points.clear();
            for (int i = 0; i<pointCount; i++) {
                Point point = new Point(random.nextInt(width+1), random.nextInt(height+1));
                points.add(point);
                if(i > 0) {
                    int edgeCount = random.nextInt(i < maxEdgeCount ? i : maxEdgeCount) + 1;
                    for(int j=0; j<edgeCount; j++) {
                        Point neighborPoint = points.get(random.nextInt(i));
                        point.addNeighborPoint(neighborPoint);
                        neighborPoint.addNeighborPoint(point);
                    }
                }
            }
        }
    }

    /**
     * prim算法（贪心算法的一种）
     * 从一幅图中生成最小生成树
     * @param graph
     */
    public static Graph prim(Graph graph) {
        if(graph != null && graph.getPoints() != null && graph.getPoints().size() > 0) {

            //清除访问标记，不然会对后面的逻辑产生影响
            for(Point checkPoint : graph.getPoints()) {
                checkPoint.setVisited(false);
            }

            List<Point> points = new ArrayList<>();
            Point rootPoint = graph.getPoints().get(0);
            rootPoint.visit();
            points.add(new Point(rootPoint.getX(), rootPoint.getY()));
            Graph minimumSpanningTree = new Graph(points);
            while (points.size() < graph.getPoints().size()) {
                float minDistance = 0;
                Point minStartPoint = null;
                Point minEndPoint = null;

                for(Point point : graph.getPoints()) {
                    if(point.isVisited()) {
                        Set<Point> neighborPoints = point.getNeighborPoints();
                        if (neighborPoints != null && neighborPoints.size() > 0) {
                            for (Point neighborPoint : neighborPoints) {
                                if (!neighborPoint.isVisited()) {
                                    float distance = (point.getX() - neighborPoint.getX()) * (point.getX() - neighborPoint.getX())
                                            + (point.getY() - neighborPoint.getY()) * (point.getY() - neighborPoint.getY());
                                    if (minDistance == 0 || distance < minDistance) {
                                        minDistance = distance;
                                        minStartPoint = point;
                                        minEndPoint = neighborPoint;
                                    }
                                }
                            }
                        }
                    }
                }

                minEndPoint.visit();
                minStartPoint = new Point(minStartPoint.getX(), minStartPoint.getY());
                minEndPoint = new Point(minEndPoint.getX(), minEndPoint.getY());
                minStartPoint.addNeighborPoint(minEndPoint);
                minEndPoint.addNeighborPoint(minStartPoint);
                points.add(minEndPoint);
                minDistance = 0;
                minStartPoint = null;
                minEndPoint = null;
            }
            return minimumSpanningTree;
        } else {
            throw new RuntimeException("图为空");
        }
    }
}
