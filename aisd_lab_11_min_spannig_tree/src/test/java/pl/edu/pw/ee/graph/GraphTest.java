package pl.edu.pw.ee.graph;

import static java.lang.String.format;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.Test;
import static org.junit.Assert.*;

public class GraphTest {

    Graph graph;

    private String getGraphAsStr(String fileName) {
        graph = new Graph(getFilePath(fileName));
        return graph.toString();
    }

    @Test
    public void shouldCorrectlyReadSmallBasicFile() {
        String expected = "Edge[src: Node: B, dest: Node: C, weight: 1,000000]\n"
                + "Edge[src: Node: C, dest: Node: D, weight: 1,000000]\n"
                + "Edge[src: Node: A, dest: Node: B, weight: 3,000000]\n"
                + "Edge[src: Node: A, dest: Node: C, weight: 5,000000]\n"
                + "Edge[src: Node: A, dest: Node: D, weight: 7,000000]\n"
                + "Edge[src: Node: D, dest: Node: E, weight: 7,000000]\n";
        //given
        String graphAsStr = getGraphAsStr("correct_small_data.txt");
        // then

        assertEquals(expected, graphAsStr);
    }

    @Test
    public void shouldCorrectlyReadSingleEdgeFile() {
        String expected = "Edge[src: Node: A, dest: Node: B, weight: 4,000000]\n";
        assertEquals(expected, getGraphAsStr("single_edge.txt"));
    }

    @Test
    public void shouldCorrectlyReadRepeatedSmallData() {
        String expected = "Edge[src: Node: B, dest: Node: C, weight: 1,000000]\n"
                + "Edge[src: Node: C, dest: Node: D, weight: 1,000000]\n"
                + "Edge[src: Node: E, dest: Node: D, weight: 1,000000]\n"
                + "Edge[src: Node: A, dest: Node: B, weight: 3,000000]\n"
                + "Edge[src: Node: A, dest: Node: C, weight: 5,000000]\n"
                + "Edge[src: Node: A, dest: Node: D, weight: 7,000000]\n"
                + "Edge[src: Node: D, dest: Node: E, weight: 7,000000]\n";
        assertEquals(expected, getGraphAsStr("correct_repeated_small_data.txt"));
    }

    @Test
    public void shouldCorrectlyReadExtendedSmallData() {
        String expected = "Edge[src: Node: A, dest: Node: C, weight: 3,162278]\n"
                + "Edge[src: Node: C, dest: Node: D, weight: 6,082763]\n"
                + "Edge[src: Node: B, dest: Node: C, weight: 6,324555]\n"
                + "Edge[src: Node: A, dest: Node: D, weight: 6,403124]\n"
                + "Edge[src: Node: A, dest: Node: B, weight: 7,071068]\n"
                + "Edge[src: Node: D, dest: Node: E, weight: 8,246211]\n";
        assertEquals(expected, getGraphAsStr("extended_small_data.txt"));
    }

    @Test
    public void shouldCorrectlyReadExtendedRepeatedSmallData() {
        String expected = "Edge[src: Node: C, dest: Node: D, weight: 2,236068]\n"
                + "Edge[src: Node: A, dest: Node: C, weight: 3,162278]\n"
                + "Edge[src: Node: D, dest: Node: E, weight: 3,162278]\n"
                + "Edge[src: Node: A, dest: Node: D, weight: 5,000000]\n"
                + "Edge[src: Node: B, dest: Node: C, weight: 6,324555]\n"
                + "Edge[src: Node: A, dest: Node: B, weight: 7,071068]\n";
        assertEquals(expected, getGraphAsStr("extended_repeated_small_data.txt"));
    }

    @Test
    public void shouldCorrectlyReadExtendedMixedData() {
        String expected = "Edge[src: Node: C, dest: Node: D, weight: 6,082763]\n"
                + "Edge[src: Node: B, dest: Node: C, weight: 6,324555]\n"
                + "Edge[src: Node: D, dest: Node: E, weight: 8,246211]\n";
        assertEquals(expected, getGraphAsStr("mix_extended_small_data.txt"));
    }

    private String getFilePath(String filename) {
        ClassLoader clsLoader = getClass().getClassLoader();

        try {
            Path path = Paths.get(clsLoader.getResource(filename).toURI());

            return path.toFile().getAbsolutePath();

        } catch (URISyntaxException e) {
            throw new RuntimeException(format("Cannot read file from filename: %s.", filename), e);
        }
    }

}
