package pl.edu.pw.ee;

import org.junit.Before;

public class HeapSortTest extends SortTestBase {

    @Before
    public void setUp() {
        sort = new HeapSort();
        testArrayMaxSize = 10000000;
    }

}
