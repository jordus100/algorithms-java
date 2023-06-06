package pl.edu.pw.ee.graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.String.format;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import pl.edu.pw.ee.exception.ReadingGraphFromFileException;

public class GraphReader {

    private final String twoNodesAndWeightLineRegex = "^[A-Za-z]+ [A-Za-z]+ \\d+$";
    private final String nodeRegex = "^[A-Za-z]+ [-]?\\d+ [-]?\\d+$";
    private final String edgeRegex = "^[A-Za-z]+ [A-Za-z]+";
    private final Pattern patternTwoNodesAndWeight = Pattern.compile(twoNodesAndWeightLineRegex);
    private final Pattern patternNode = Pattern.compile(nodeRegex);
    private final Pattern patternEdge = Pattern.compile(edgeRegex);

    private final String fileToPath;
    private List<Edge> edges;
    private Map<String, Node> nodes;

    public GraphReader(String fileToPath) {
        validateData(fileToPath);

        this.fileToPath = fileToPath;
        this.edges = new ArrayList<>();
        this.nodes = new HashMap<>();

        readAndSort();
    }

    public List<Edge> getEdges() {
        return edges;
    }

    private void validateData(String fileToPath) {
        if (fileToPath == null) {
            throw new IllegalArgumentException("File to path arg cannot be null!");
        }
    }

    private void readAndSort() {
        readGraphFromFile();
        sortEdgesByPriority();
    }

    private void readGraphFromFile() {
        Edge edge;
        int i = 1;

        try ( BufferedReader reader = new BufferedReader(new FileReader(fileToPath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                edge = parseToEdge(line, i);
                if (edge != null) {
                    if (!edges.contains(edge)) {
                        edges.add(edge);
                    } else {
                        edges.remove(edge);
                        edges.add(edge);
                    }
                }
                i++;
            }
        } catch (IOException e) {
            throw new ReadingGraphFromFileException("Cannot read file from path!", e);
        }
    }

    private Edge parseToEdge(String line, int lineNumber) {
        String[] args;
        Node start;
        Node end;
        int weight;
        Edge edgeReturn = null;

        line = stripComment(line);
        line = line.trim();
        Matcher twoNodesAndWeightMatcher = patternTwoNodesAndWeight.matcher(line);
        Matcher nodeMatcher = patternNode.matcher(line);
        Matcher edgeMatcher = patternEdge.matcher(line);

        if (twoNodesAndWeightMatcher.find()) {
            args = line.split(" ");
            start = new Node(args[0], 0, 0);
            end = new Node(args[1], 0, 0);
            weight = Integer.parseInt(args[2]);

            edgeReturn = new Edge(start, end, weight);

        } else if (nodeMatcher.find()) {
            args = line.split(" ");
            nodes.put(args[0], new Node(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2])));
        } else if (edgeMatcher.find()) {
            args = line.split(" ");
            String node1 = args[0];
            String node2 = args[1];
            if (nodes.containsKey(node1) && nodes.containsKey(node2)) {
                double edgeWeight = Math.sqrt(Math.pow(nodes.get(node2).x - nodes.get(node1).x, 2.0) + Math.pow(nodes.get(node2).y - nodes.get(node1).y, 2.0));
                edgeReturn = new Edge(nodes.get(node1), nodes.get(node2), edgeWeight);
            }
        }
        return edgeReturn;
    }

    private String stripComment(String line) {
        if (line.contains("#")) {
            return line.substring(0, line.indexOf('#'));
        } else {
            return line;
        }
    }

    private void sortEdgesByPriority() {
        EdgeWeightComparator weightComparator = new EdgeWeightComparator();
        Collections.sort(edges, weightComparator);
    }

}
