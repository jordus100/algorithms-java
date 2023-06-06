package pl.edu.pw.ee;

import org.junit.Before;

public class QuickSortPerformanceTest extends PerformanceTestBase {

    @Before
    public void setUp() {
        sort = new QuickSort();
        maxTestArraySize = 100000;
        fileName = "Quick";
    }

}
