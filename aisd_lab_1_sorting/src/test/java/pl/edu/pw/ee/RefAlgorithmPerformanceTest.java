package pl.edu.pw.ee;

import org.junit.Before;

public class RefAlgorithmPerformanceTest extends PerformanceTestBase{
    
    @Before
    public void setUp() {
        sort = new RefAlgorithm();
        maxTestArraySize = 30000000;
        fileName = "Ref";
    }
    
}
