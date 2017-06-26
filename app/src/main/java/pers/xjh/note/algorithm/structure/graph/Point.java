package pers.xjh.note.algorithm.structure.graph;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by XJH on 2017/6/8.
 */

public class Point {

    private float x;

    private float y;
    //邻近点
    private Set<Point> neighborPoints;

    //记录节点是否被访问过
    private boolean visited;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Set<Point> getNeighborPoints() {
        return neighborPoints;
    }

    public void addNeighborPoint(Point neighborPoint) {
        if(neighborPoints == null) {
            neighborPoints = new HashSet<>();
        }
        neighborPoints.add(neighborPoint);
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
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
