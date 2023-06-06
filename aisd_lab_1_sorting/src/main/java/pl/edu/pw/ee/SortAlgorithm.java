package pl.edu.pw.ee;

import java.util.ArrayList;
import java.util.List;

public class SortAlgorithm {

    protected void swap(double[] nums, int firstId, int secondId) {
        if (firstId != secondId) {
            double firstVal = nums[firstId];
            nums[firstId] = nums[secondId];
            nums[secondId] = firstVal;
        }
    }

    protected <T> void listSwap(List<T> list, int firstId, int secondId) {
        if (firstId != secondId) {
            T firstVal = list.get(firstId);
            list.set(firstId, list.get(secondId));
            list.set(secondId, firstVal);
        }
    }
}
