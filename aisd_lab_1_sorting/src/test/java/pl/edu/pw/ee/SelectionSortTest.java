package pl.edu.pw.ee;

import org.junit.Before;

public class SelectionSortTest extends SortTestBase {

    @Before
    public void setUp() {
        sort = new SelectionSort();
        testArrayMaxSize = 200000;
    }

}
