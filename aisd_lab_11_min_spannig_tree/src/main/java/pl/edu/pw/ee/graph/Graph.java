package pl.edu.pw.ee.graph;

import static java.lang.String.format;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Graph {

    private final String pathToGraphFile;
    private List<Edge> edges;

    public Graph(String pathToGraphFile) {
        this.pathToGraphFile = pathToGraphFile;

        readGraphFromFile();
    }

    @Override
    public String toString() {
        StringBuilder graphAsStr = new StringBuilder();

        for (Edge edge : edges) {
            graphAsStr.append(edge).append("\n");
        }

        return graphAsStr.toString();
    }

    private void validateParams(String pathToGraphFile) {
        if (pathToGraphFile == null) {
            throw new IllegalArgumentException("Path to graph file cannot be null!");
        }
    }

    private void readGraphFromFile() {
        GraphReader reader = new GraphReader(pathToGraphFile);
        edges = reader.getEdges();
    }

}
