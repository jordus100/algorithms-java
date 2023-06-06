package pl.edu.pw.ee;

import java.util.Random;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import pl.edu.pw.ee.services.Sorting;

public class SortTestBase {

    private final double EPS = 0.0;
    private final long randomizerSeed = 10L;

    protected Sorting sort;
    protected int testArrayMaxSize;

    @Test(expected = IllegalArgumentException.class)
    public void inputArrayNull() {
        //given
        double[] nums = null;
        //when
        sort.sort(nums);
        //then
        assert false;
    }

    @Test
    public void emptyInputArray() {
        //given
        double[] nums = {};
        //when
        sort.sort(nums);
        //then
        assertEquals(nums.length, 0);
    }

    @Test
    public void oneElementInputArray() {
        //given
        double[] nums = {0.0};
        //when
        sort.sort(nums);
        //then
        assertEquals(nums.length, 1);
        assertEquals(0, nums[0], EPS);
    }

    @Test
    public void inputArrayAlreadySorted() {
        //given
        double[] nums = {1, 2, 3, 4, 5};
        //when
        sort.sort(nums);
        //then
        double[] expected = {1, 2, 3, 4, 5};
        Assert.assertArrayEquals(expected, nums, EPS);
    }

    @Test
    public void inputArraySortedInReverseOrder() {
        //given
        double[] nums = {5, 4, 3, 2, 1};
        //when
        sort.sort(nums);
        //then
        double[] expected = {1, 2, 3, 4, 5};
        Assert.assertArrayEquals(expected, nums, EPS);
    }

    @Test
    public void inputArrayUnsortedWithoutRepetitions() {
        //given
        double[] nums = {2, 5, 6, 9, 1, 8, 10, 4, 7, 3};
        //when
        sort.sort(nums);
        //then
        double[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Assert.assertArrayEquals(expected, nums, EPS);
    }

    @Test
    public void inputArrayUnsortedWithRepetitions() {
        //given
        double[] nums = {5, 5, 6, 4, 1, 8, 10, 4, 7, 3};
        //when
        sort.sort(nums);
        //then
        double[] expected = {1, 3, 4, 4, 5, 5, 6, 7, 8, 10};
        Assert.assertArrayEquals(expected, nums, EPS);
    }

    @Test
    public void largeRandomInputArray() {
        //given
        Random r = new Random(randomizerSeed);
        double[] nums = new double[testArrayMaxSize];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = r.nextDouble(Double.MIN_VALUE, Double.MAX_VALUE);
        }
        //when
        sort.sort(nums);
        //then (don't crash)
    }
}
