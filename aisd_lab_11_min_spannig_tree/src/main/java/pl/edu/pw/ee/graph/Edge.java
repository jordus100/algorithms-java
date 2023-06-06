package pl.edu.pw.ee.graph;

import static java.lang.String.format;
import java.util.Objects;

public class Edge {

    private final Node src;
    private final Node dest;
    private final double weight;

    public Edge(Node src, Node dest, double weight) {
        validateInput(src, dest);

        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }
    
    @Override
    public boolean equals(Object o){
        if(o instanceof Edge && ((this.src == ((Edge)(o)).src && this.dest == ((Edge)(o)).dest) ||
                (this.src == ((Edge)(o)).dest && this.dest == ((Edge)(o)).src))) return true;
        else return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.src);
        hash = 59 * hash + Objects.hashCode(this.dest);
        return hash;
    }

    public Node getSrc() {
        return src;
    }

    public Node getDest() {
        return dest;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return format("Edge[src: %s, dest: %s, weight: %f]", src, dest, weight);
    }

    private void validateInput(Node start, Node end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Input params (start or end) cannot be null!");
        }
    }

}
