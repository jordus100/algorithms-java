package pl.edu.pw.ee.graph;

import static java.lang.String.format;

public class Node {

    public String name;
    public int x, y;

    public Node(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return format("Node: %s", name);
    }
}
