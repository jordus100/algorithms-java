package pl.edu.pw.ee;

import org.junit.Test;
import pl.edu.pw.ee.services.HashTable;

public class HashQuadraticProbingTest extends HashOpenAddressingTestBase {

    @Override
    protected <T extends Comparable<T>> HashTable<T> getInstance() {
        return new HashQuadraticProbing<T>();
    }

    @Override
    protected <T extends Comparable<T>> HashTable<T> getInstance(int size) {
        return new HashQuadraticProbing<T>(size, 1, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_prevent_creating_hash_instance_with_nonpositive_constants() {
        HashTable<String> test = new HashQuadraticProbing<String>(2039, 0, -2);
    }

}
