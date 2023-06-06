package pl.edu.pw.ee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import pl.edu.pw.ee.services.HashTable;

public class PerformanceTest {

    String[] wordList = new String[100000];
    int[] base2Sizes = new int[10];
    HashTable<String> hashTable;

    enum HashType {
        Linear, Quadratic, Double
    }

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
        for (int i = 262144; i >= 512; i /= 2) {
            base2Sizes[ind] = i;
            ind++;
        }
        Arrays.sort(base2Sizes);
    }

    @Test
    public void performTest() {
        testHashSizes(base2Sizes, "Adresowanie liniowe", HashType.Linear);
        testHashSizes(base2Sizes, "Adresowanie kwadratowe", HashType.Quadratic);
        testHashSizes(base2Sizes, "Adresowanie dwukrotne", HashType.Double);
    }

    private void testHashSizes(int[] sizes, String name, HashType hashType) {
        long results[] = new long[30];
        System.out.println("Name:" + name);
        for (int i = 0; i < sizes.length; i++) {
            for (int j = 0; j < 30; j++) {
                results[j] = testSearchingTime(getHashInstance(hashType, sizes[i]));
            }
            Arrays.sort(results);
            int sum = 0;
            for (int n = 9; n < 20; n++) {
                sum += results[i];
            }
            System.out.println(sizes[i] + " : " + sum / 10 + " ms");
        }
    }

    private long testSearchingTime(HashTable<String> hashTable) {
        Long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            hashTable.put(wordList[i]);
        }
        Long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    private HashTable<String> getHashInstance(HashType hashType, int size) {
        switch (hashType) {
            case Linear:
                return new HashLinearProbing<String>(size);
            case Quadratic:
                return new HashQuadraticProbing<String>(size, 1, 4);
            case Double:
                return new HashDoubleHashing<String>(size);
        }
        return null;
    }

}
