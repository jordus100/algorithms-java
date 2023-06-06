package pl.edu.pw.ee;

import org.junit.Before;

public class QuickSortTest extends SortTestBase {

    @Before
    public void setUp() {
        sort = new QuickSort();
        testArrayMaxSize = 100000000;
    }

}
