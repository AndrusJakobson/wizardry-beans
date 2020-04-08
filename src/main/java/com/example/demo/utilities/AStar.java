// Used material
// https://rosettacode.org/wiki/A*_search_algorithm#Java

package com.example.demo.utilities;

import com.example.demo.models.common.Coordinate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AStar {
    private List<Node> open;
    private List<Node> closed;
    private List<Node> path;
    private int[][] maze;
    private Node now;
    private int xstart;
    private int ystart;
    private int xend, yend;
    private final boolean diag;

    public static class Node implements Comparable {
        public Node parent;
        public int x, y;
        public double g;
        public double h;

        Node(Node parent, int xpos, int ypos, double g, double h) {
            this.parent = parent;
            this.x = xpos;
            this.y = ypos;
            this.g = g;
            this.h = h;
        }

        @Override
        public int compareTo(Object o) {
            Node that = (Node) o;
            return (int) ((this.g + this.h) - (that.g + that.h));
        }
    }

    public AStar(int[][] maze, int xstart, int ystart, boolean diag) {
        this.open = new ArrayList<>();
        this.closed = new ArrayList<>();
        this.path = new ArrayList<>();
        this.maze = maze;
        this.now = new Node(null, xstart, ystart, 0, 0);
        this.xstart = xstart;
        this.ystart = ystart;
        this.diag = diag;
    }


    public void initVariables(int xStart, int yStart) {
        this.open = new ArrayList<>();
        this.closed = new ArrayList<>();
        this.path = new ArrayList<>();
        this.now = new Node(null, xStart, yStart, 0, 0);
        this.xstart = xStart;
        this.ystart = yStart;
    }

    public Coordinate findNextStep(int xend, int yend) {
        this.xend = xend;
        this.yend = yend;
        this.closed.add(this.now);
        addNeighboursToOpenList();
        while (this.now.x != this.xend || this.now.y != this.yend) {
            if (this.open.isEmpty()) {
                return new Coordinate(this.xstart, this.ystart);
            }
            this.now = this.open.get(0);
            this.open.remove(0);
            this.closed.add(this.now);
            addNeighboursToOpenList();
        }
        this.path.add(0, this.now);
        while (this.now.x != this.xstart || this.now.y != this.ystart) {
            this.now = this.now.parent;
            this.path.add(0, this.now);
        }

        ArrayList<Node> currentPath = new ArrayList<>(this.path);
        this.path.clear();

        Coordinate nextCoordinate;
        if (currentPath.size() < 2) {
            nextCoordinate = new Coordinate(currentPath.get(0).x, currentPath.get(0).y);
        } else {
            nextCoordinate = new Coordinate(currentPath.get(1).x, currentPath.get(1).y);
        }
        return nextCoordinate;
    }

    private static boolean findNeighborInList(List<Node> array, Node node) {
        return array.stream().anyMatch((n) -> (n.x == node.x && n.y == node.y));
    }

    private double distance(int dx, int dy) {
        if (this.diag) {
            return Math.hypot(this.now.x + dx - this.xend, this.now.y + dy - this.yend);
        } else {
            return Math.abs(this.now.x + dx - this.xend) + Math.abs(this.now.y + dy - this.yend);
        }
    }

    private void addNeighboursToOpenList() {
        Node node;
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (!this.diag && x != 0 && y != 0) {
                    continue;
                }
                node = new Node(this.now, this.now.x + x, this.now.y + y, this.now.g, this.distance(x, y));
                if ((x != 0 || y != 0)
                        && this.now.x + x >= 0 && this.now.x + x < this.maze[0].length
                        && this.now.y + y >= 0 && this.now.y + y < this.maze.length
                        && this.maze[this.now.y + y][this.now.x + x] != -1
                        && !findNeighborInList(this.open, node) && !findNeighborInList(this.closed, node)) {
                    node.g = node.parent.g + 1.;
                    node.g += maze[this.now.y + y][this.now.x + x];

                    this.open.add(node);
                }
            }
        }
        Collections.sort(this.open);
    }
}