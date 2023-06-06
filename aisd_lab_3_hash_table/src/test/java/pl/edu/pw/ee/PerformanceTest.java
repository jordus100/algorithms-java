package pl.edu.pw.ee;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import pl.edu.pw.ee.services.HashTable;

public class PerformanceTest {

    String[] wordList = new String[100000];
    int[] base2Sizes = new int[7];
    int[] primeSizes = {4093, 8419, 16553, 33107, 65003, 130513, 262147};
    HashTable<String> hashTable;

    @Before
    public void setUp() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("wordlist.txt"));
            for (int i = 0; i < 100000; i++) {
                wordList[i] = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        int ind = 0;
        for (int i = 4096; i <= 262144; i *= 2) {
            base2Sizes[ind] = i;
            ind++;
        }
    }

    @Test
    public void performTest() {
        testHashSizes(base2Sizes, "Base 2 sizes");
        testHashSizes(primeSizes, "Prime sizes");
    }

    private void testHashSizes(int[] sizes, String name) {
        long results[] = new long[30];
        System.out.println("Name:" + name);
        for (int i = 0; i < sizes.length; i++) {
            for (int j = 0; j < 30; j++) {
                results[j] = testSearchingTime(sizes[i]);
            }
            Arrays.sort(results);
            int sum = 0;
            for (int n = 9; n < 20; n++) {
                sum += results[i];
            }
            System.out.println(sizes[i] + " : " + sum / 10 + " ms");
        }
    }

    private long testSearchingTime(int size) {
        hashTable = new HashListChaining<String>(size);
        for (int i = 0; i < 100000; i++) {
            hashTable.add(wordList[i]);
        }
        Long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            hashTable.get(wordList[i]);
        }
        Long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

}
