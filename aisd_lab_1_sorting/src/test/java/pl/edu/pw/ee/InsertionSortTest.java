package pl.edu.pw.ee;

import org.junit.Before;

public class InsertionSortTest extends SortTestBase {

    @Before
    public void setUp() {
        sort = new InsertionSort();
        testArrayMaxSize = 200000;
    }

}
