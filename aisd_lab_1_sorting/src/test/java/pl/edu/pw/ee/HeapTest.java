package pl.edu.pw.ee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class HeapTest {

    private List<Double> heapArranged;

    @Before
    public void setUp() {
        heapArranged = new ArrayList<>(Arrays.asList(new Double[]
        {15.0, 14.0, 13.0, 12.0, 10.0, 11.0, 8.0, 6.0, 3.0, 5.0, 1.0}));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shoudlPreventPassingNullIntoHeap() {
        Heap heap = new Heap(heapArranged);
        heap.put(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldPreventPassingImmutableList() {
        List<Double> immutableList = Arrays.asList(new Double[]{1.0});
        Heap heap = new Heap(immutableList);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldPreventDoingOperationsOnNullElements() {
        Heap heap = new Heap(heapArranged);
        heapArranged.add(0, null);
        heap.heapify(0, heapArranged.size());
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void shouldPreventPoppingFromEmptyHeap() {
        List<Double> emptyHeap = new ArrayList<Double>();
        Heap heap = new Heap(emptyHeap);
        heap.pop();
    }

    @Test
    public void putIntoHeap() {
        //when
        Heap heap = new Heap(heapArranged);
        heap.put(16.0);
        //then
        List<Double> expected = Arrays.asList(new Double[]
        {15.0, 14.0, 13.0, 12.0, 10.0, 11.5, 8.0, 6.0, 3.0, 5.0, 1.0, 11.0});
        assertEquals(16.0, heap.pop());
    }

    @Test
    public void popFromHeap() {
        Heap heap = new Heap(heapArranged);
        assertEquals(15.0, heap.pop());
        assertEquals(14.0, heap.pop());
    }

    @Test
    public void testHeapBuilding() {
        //given
        List<Double> heapDisarranged = new ArrayList<>(Arrays.asList(new Double[]
        {10.0, 6.0, 12.0, 1.0, 15.0, 11.0, 5.0, 14.0, 3.0, 8.0, 13.0}));
        //when
        Heap heap = new Heap(heapDisarranged);
        //then
        assertEquals(15.0, heap.pop());
        assertEquals(14.0, heap.pop());
    }
}
