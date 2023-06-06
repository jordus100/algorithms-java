package pl.edu.pw.ee;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.Test;
import pl.edu.pw.ee.services.Sorting;

public class PerformanceTestBase {

    private enum TestSortType {
        PESSIMISTIC, OPTIMISTIC, RANDOM
    }

    protected int maxTestArraySize;
    protected String fileName;
    protected Sorting sort;

    private double[] generatePessimisticData(int size) {
        double[] testData = new double[size];
        double span = Double.MAX_VALUE - Double.MAX_VALUE;
        double step = span / size;
        double nextValue = Double.MAX_VALUE;
        for (int i = 0; i < size; i++) {
            testData[i] = nextValue;
            nextValue -= step;
        }
        return testData;
    }

    private double[] generateOptimisticData(int size) {
        double[] testData = new double[size];
        double span = Double.MAX_VALUE - Double.MAX_VALUE;
        double step = span / size;
        double nextValue = Double.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            testData[i] = nextValue;
            nextValue += step;
        }
        return testData;
    }

    private double[] generateRandomData(int size) {
        double[] testData = new double[size];
        Random r = new Random(2137);
        for (int i = 0; i < size; i++) {
            testData[i] = r.nextDouble(Double.MIN_VALUE, Double.MAX_VALUE);
        }
        return testData;
    }

    private void saveToFile(String name, int[] sizes, float[] times) throws IOException {
        FileWriter fw = new FileWriter(name);
        PrintWriter printWriter = new PrintWriter(fw);
        for (int i = 0; i < sizes.length; i++) {
            printWriter.print(String.valueOf(sizes[i]) + " " + String.valueOf(times[i]) + "\n");
        }
        printWriter.close();
    }

    @Test
    public void testPerformance() {
        testSortingTimes(fileName + "Pes", TestSortType.PESSIMISTIC);
        testSortingTimes(fileName + "Opt", TestSortType.OPTIMISTIC);
        testSortingTimes(fileName + "Rand", TestSortType.RANDOM);
    }

    private void testSortingTimes(String fileName, TestSortType testType) {
        double[] testData = null;
        int[] sizes = new int[(int) (customLog((double) 1.4, (double) maxTestArraySize) - 4)];
        float[] times = new float[sizes.length];
        int n = 0;
        for (int i = 8; i < maxTestArraySize; i *= 1.4) {
            switch (testType) {
                case PESSIMISTIC:
                    testData = generatePessimisticData(i);
                    break;
                case OPTIMISTIC:
                    testData = generateOptimisticData(i);
                    break;
                case RANDOM:
                    testData = generateRandomData(i);
                    break;
            }
            float meanSum = 0f;
            for (int j = 0; j < 4; j++) {
                long start = System.nanoTime();
                sort.sort(testData);
                long finish = System.nanoTime();
                meanSum += (finish - start);
            }
            float timeElapsed = meanSum / 5;
            sizes[n] = i;
            times[n] = timeElapsed;
            n++;
        }
        try {
            saveToFile(fileName, sizes, times);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private double customLog(double base, double logNumber) {
        return Math.log(logNumber) / Math.log(base);
    }
}
