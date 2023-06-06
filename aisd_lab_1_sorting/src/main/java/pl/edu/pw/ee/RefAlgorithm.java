package pl.edu.pw.ee;

import java.util.Arrays;
import pl.edu.pw.ee.services.Sorting;

public class RefAlgorithm implements Sorting {

    @Override
    public void sort(double[] nums) {
        if (nums == null) {
            throw new IllegalArgumentException("nums cannot be null");
        }
        Arrays.sort(nums);
    }

}
