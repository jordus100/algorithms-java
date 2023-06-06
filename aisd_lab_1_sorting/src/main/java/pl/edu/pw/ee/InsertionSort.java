package pl.edu.pw.ee;

import pl.edu.pw.ee.services.Sorting;

public class InsertionSort extends SortAlgorithm implements Sorting {

    @Override
    public void sort(double[] nums) {
        if (nums == null) {
            throw new IllegalArgumentException("Input array can't be null");
        }
        for (int i = 1; i < nums.length; i++) {
            int j = i;
            while (j > 0 && nums[j - 1] > nums[j]) {
                swap(nums, j - 1, j);
                j--;
            }
        }
    }

}
