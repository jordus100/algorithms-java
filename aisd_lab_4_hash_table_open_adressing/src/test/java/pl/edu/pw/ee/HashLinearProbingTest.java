package pl.edu.pw.ee;

import pl.edu.pw.ee.services.HashTable;

public class HashLinearProbingTest extends HashOpenAddressingTestBase {

    @Override
    protected <T extends Comparable<T>> HashTable<T> getInstance() {
        return new HashLinearProbing<T>();
    }

    @Override
    protected <T extends Comparable<T>> HashTable<T> getInstance(int size) {
        return new HashLinearProbing<T>(size);
    }

}
