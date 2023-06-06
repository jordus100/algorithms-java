package pl.edu.pw.ee;

import org.junit.Before;

public class RefAlgorithmTest extends SortTestBase {

    @Before
    public void setUp() {
        sort = new RefAlgorithm();
        testArrayMaxSize = 100000000;
    }

}
