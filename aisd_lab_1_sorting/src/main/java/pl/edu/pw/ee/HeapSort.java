package pl.edu.pw.ee;

import java.util.ArrayList;
import java.util.List;
import pl.edu.pw.ee.services.HeapExtension;
import pl.edu.pw.ee.services.Sorting;

public class HeapSort extends SortAlgorithm implements Sorting {

    private List<Double> data;
    private HeapExtension heap;

    @Override
    public void sort(double[] nums) {
        if (nums == null) {
            throw new IllegalArgumentException("Input array cannot be null!");
        }

        int n = nums.length;

        data = boxingData(nums);
        heap = new Heap(data);

        for (int i = n - 1; i >= 0; i--) {
            nums[i] = data.get(0);
            listSwap(data, 0, i);
            data.remove(i);
            heap.heapify(0, i);
        }
    }

    private List<Double> boxingData(double[] nums) {
        List<Double> numsAsList = new ArrayList<>();

        for (Double num : nums) {
            numsAsList.add(num);
        }

        return numsAsList;
    }

}
