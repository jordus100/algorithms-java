package pl.edu.pw.ee;

import pl.edu.pw.ee.services.HashTable;

public class HashDoubleHashingTest extends HashOpenAddressingTestBase {

    @Override
    protected <T extends Comparable<T>> HashTable<T> getInstance() {
        return new HashDoubleHashing<T>();
    }

    @Override
    protected <T extends Comparable<T>> HashTable<T> getInstance(int size) {
        return new HashDoubleHashing<T>(size);
    }

}
