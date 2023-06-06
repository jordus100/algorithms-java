package pl.edu.pw.ee;

import pl.edu.pw.ee.services.Sorting;

public class SelectionSort extends SortAlgorithm implements Sorting {

    @Override
    public void sort(double[] nums) {
        if (nums == null) {
            throw new IllegalArgumentException("Input array can't be null");
        }
        for (int i = 0; i < nums.length - 1; i++) {
            int minValId = i;
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] < nums[minValId]) {
                    minValId = j;
                }
            }
            swap(nums, i, minValId);
        }
    }

}
