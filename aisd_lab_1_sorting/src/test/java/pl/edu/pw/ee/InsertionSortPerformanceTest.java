package pl.edu.pw.ee;

import org.junit.Before;

public class InsertionSortPerformanceTest extends PerformanceTestBase {

    @Before
    public void setUp() {
        sort = new InsertionSort();
        maxTestArraySize = 160000;
        fileName = "Insertion";
    }

}
