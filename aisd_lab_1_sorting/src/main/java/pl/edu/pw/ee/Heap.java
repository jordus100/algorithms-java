package pl.edu.pw.ee;

import java.util.ArrayList;
import java.util.List;
import pl.edu.pw.ee.services.HeapExtension;
import pl.edu.pw.ee.services.HeapInterface;

public class Heap<T extends Comparable<T>> extends SortAlgorithm
        implements HeapInterface<T>, HeapExtension {

    private List<T> data;

    public Heap(List<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        try {
            data.add(null);
        } catch (UnsupportedOperationException e) {
            throw new IllegalArgumentException("Data list has to be mutable");
        }
        data.remove(data.size() - 1);
        this.data = data;
        build();
    }

    @Override
    public void put(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Item to be put cannot be null");
        }
        heapify(0, data.size());
        int i = data.size();
        data.add(item);
        if (data.get(i) == null) {
            throw new IllegalStateException("Elements in heap cannot be null");
        }
        int parentIndex;
        while (i > 0) {
            if (i % 2 != 0) {
                parentIndex = (i - 1) / 2;
            } else {
                parentIndex = (i - 2) / 2;
            }
            if (data.get(parentIndex) == null) {
                throw new IllegalStateException("Elements in heap cannot be null");
            }
            if (data.get(parentIndex).compareTo(data.get(i)) < 0) {
                listSwap((ArrayList) data, i, parentIndex);
                i = parentIndex;
            } else {
                return;
            }
        }
    }

    @Override
    public T pop() {
        if (data.size() == 0) {
            throw new ArrayIndexOutOfBoundsException("Heap has to contain elements to pop");
        }
        heapify(0, data.size());
        listSwap((ArrayList) data, 0, data.size() - 1);
        T poppedElement = data.remove(data.size() - 1);
        heapify(0, data.size() - 1);
        if (poppedElement == null) {
            throw new IllegalStateException("Elements in heap cannot be null");
        }
        return poppedElement;
    }

    @Override
    public void build() {
        int n = data.size();
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(i, n);
        }
    }

    @Override
    public void heapify(int startId, int endId) {
        if (startId < 0) {
            throw new IllegalArgumentException(
                    "startId cannot be negative");
        }
        int i = startId;
        if (endId > data.size()) {
            endId = data.size();
        }
        int indexToCompare;
        while (i < endId && i < data.size()) {
            indexToCompare = compareChildren(i);
            if (data.get(i) == null) {
                throw new IllegalStateException("Elements in heap cannot be null");
            }
            if (data.get(i).compareTo(data.get(indexToCompare)) < 0) {
                listSwap(data, i, indexToCompare);
                i = indexToCompare;
                continue;
            } else {
                return;
            }
        }
    }

    private int compareChildren(int index) {
        int leftChildIndex = index * 2 + 1;
        int rightChildIndex = index * 2 + 2;
        if (leftChildIndex >= data.size()) {
            return index;
        }
        if (rightChildIndex >= data.size()) {
            return leftChildIndex;
        }
        if (data.get(leftChildIndex) == null || data.get(rightChildIndex) == null) {
            throw new IllegalStateException("Elements in heap cannot be null");
        }
        if (data.get(leftChildIndex).compareTo(data.get(rightChildIndex)) > 0) {
            return leftChildIndex;
        } else {
            return rightChildIndex;
        }
    }

}
