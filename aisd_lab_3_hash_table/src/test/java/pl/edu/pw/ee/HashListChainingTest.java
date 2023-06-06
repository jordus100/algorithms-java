package pl.edu.pw.ee;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import pl.edu.pw.ee.services.HashTable;

public class HashListChainingTest {

    HashTable hashTable;

    @Before
    public void setUp() {
        hashTable = new HashListChaining(1000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldPreventAddingNullValue() {
        hashTable.add(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldPreventGettingNullValue() {
        hashTable.get(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldPreventDeletingNullValue() {
        hashTable.delete(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldPreventCreatingHashOfSizeZero() {
        hashTable = new HashListChaining(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldPreventCreatingHashOfNegativeSize() {
        hashTable = new HashListChaining(-1);
    }

    @Test
    public void addAndGetElement() {
        TestElem myObj = new TestElem(100);
        hashTable.add(myObj);
        assertEquals(hashTable.get(myObj), myObj);
    }

    @Test
    public void addMoreElementsThanHashSize() {
        for (int i = 0; i < 3000; i++) {
            hashTable.add(Integer.valueOf(i));
        }
        for (int i = 0; i < 3000; i++) {
            assertEquals(i, hashTable.get(Integer.valueOf(i).intValue()));
        }
    }

    @Test(expected = UnsupportedOperationException.class)
    public void addSameElementMultipleTimes() {
        hashTable.add(Integer.valueOf(3));
        hashTable.add(Integer.valueOf(3));
    }

    @Test
    public void getMoreElementsThanHashSize() {
        for (int i = 0; i < 1000; i++) {
            hashTable.add(Integer.valueOf(i));
        }
        for (int i = 0; i < 2000; i++) {
            hashTable.get(Integer.valueOf(i));
        }
    }

    @Test
    public void addAndDeleteElement() {
        TestElem myObj = new TestElem(100);
        hashTable.add(myObj);
        hashTable.delete(myObj);
        Object obj = hashTable.get(myObj);
        assertEquals(null, obj);
    }

    @Test
    public void addAndDeleteElementsWithSameHash() {
        for (int i = 0; i < 4; i++) {
            hashTable.add(new TestElem(i));
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

}
