package pl.edu.pw.ee;

import org.junit.Before;

public class SelectionSortPerformanceTest extends PerformanceTestBase {

    @Before
    public void setUp() {
        sort = new SelectionSort();
        maxTestArraySize = 90000;
        fileName = "Selection";
    }

}
