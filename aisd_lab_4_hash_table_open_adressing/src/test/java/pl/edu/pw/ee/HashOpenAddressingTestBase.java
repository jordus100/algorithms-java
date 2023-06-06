package pl.edu.pw.ee;

import java.lang.reflect.Field;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import pl.edu.pw.ee.services.HashTable;

public abstract class HashOpenAddressingTestBase {

    protected abstract <T extends Comparable<T>> HashTable<T> getInstance();

    protected abstract <T extends Comparable<T>> HashTable<T> getInstance(int size);

    @Test(expected = IllegalArgumentException.class)
    public void shouldPreventInitializingWithSizeLowerThanOne() {
        HashTable<Double> hash = getInstance(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldPreventGettingNullValue() {
        HashTable<String> emptyHash = getInstance();
        emptyHash.get(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldPreventDeletingNullValue() {
        HashTable<String> emptyHash = getInstance();
        emptyHash.delete(null);
    }

    @Test
    public void addAndGetElements() {
        HashTable<TestElem> hashTable = getInstance();
        for (int i = 0; i < 3000; i++) {
            TestElem myObj = new TestElem(i);
            hashTable.put(myObj);
            assertEquals(i + 1, getNumOfElems(hashTable));
        }
        for (int i = 2999; i >= 0; i--) {
            TestElem myObj = new TestElem(i);
            assertEquals(myObj, hashTable.get(myObj));
        }
    }

    @Test
    public void addMoreElementsThanHashSize() {
        HashTable<Integer> hashTable = getInstance(2000);
        for (int i = 0; i < 3000; i++) {
            hashTable.put(Integer.valueOf(i));
        }
        for (int i = 0; i < 3000; i++) {
            assertEquals((Object) i, hashTable.get(Integer.valueOf(i).intValue()));
        }
    }

    @Test
    public void addSameElementMultipleTimes() {
        HashTable<Integer> hashTable = getInstance();
        hashTable.put(Integer.valueOf(3));
        assertEquals(1, getNumOfElems(hashTable));
        hashTable.put(Integer.valueOf(3));
        assertEquals(1, getNumOfElems(hashTable));
    }

    @Test
    public void deleteSameElementMultipleTimes() {
        HashTable<Integer> hashTable = getInstance();
        hashTable.put(Integer.valueOf(3));
        assertEquals(1, getNumOfElems(hashTable));
        hashTable.delete(Integer.valueOf(3));
        assertEquals(0, getNumOfElems(hashTable));
        hashTable.delete(Integer.valueOf(3));
        assertEquals(0, getNumOfElems(hashTable));
    }

    @Test
    public void getMoreElementsThanHashSize() {
        HashTable<Integer> hashTable = getInstance(1000);
        for (int i = 0; i < 1000; i++) {
            hashTable.put(Integer.valueOf(i));
        }
        for (int i = 0; i < 2000; i++) {
            hashTable.get(Integer.valueOf(i));
        }
    }

    @Test
    public void addAndDeleteElements() {
        HashTable<TestElem> hashTable = getInstance();
        for (int i = 0; i < 3000; i++) {
            TestElem myObj = new TestElem(i);
            hashTable.put(myObj);
            assertEquals(i + 1, getNumOfElems(hashTable));
        }
        for (int i = 2999; i >= 0; i--) {
            TestElem myObj = new TestElem(i);
            hashTable.delete(myObj);
            assertEquals(i, getNumOfElems(hashTable));
        }
    }

    @Test
    public void addAndDeleteElementsWithSameHash() {
        HashTable<TestElem> hashTable = getInstance();
        for (int i = 0; i < 4; i++) {
            hashTable.put(new TestElem(i));
        }
        hashTable.delete(new TestElem(1));
        assertEquals(null, hashTable.get(new TestElem(1)));
        assertEquals(new TestElem(3), hashTable.get(new TestElem(3)));
        assertEquals(new TestElem(2), hashTable.get(new TestElem(2)));
        assertEquals(new TestElem(0), hashTable.get(new TestElem(0)));
        hashTable.delete(new TestElem(3));
        assertEquals(null, hashTable.get(new TestElem(3)));
        assertEquals(new TestElem(2), hashTable.get(new TestElem(2)));
        assertEquals(new TestElem(0), hashTable.get(new TestElem(0)));
        hashTable.delete(new TestElem(0));
        assertEquals(null, hashTable.get(new TestElem(0)));
        assertEquals(new TestElem(2), hashTable.get(new TestElem(2)));
        hashTable.delete(new TestElem(2));
        assertEquals(null, hashTable.get(new TestElem(0)));
    }

    private int getNumOfElems(HashTable<?> hash) {
        String fieldNumOfElems = "nElems";
        try {
            System.out.println(hash.getClass().getSuperclass().getName());
            Field field = hash.getClass().getSuperclass().getDeclaredField(fieldNumOfElems);
            field.setAccessible(true);

            int numOfElems = field.getInt(hash);

            return numOfElems;

        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
