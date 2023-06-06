package pl.edu.pw.ee;

import pl.edu.pw.ee.services.HashTable;

public abstract class HashOpenAdressing<T extends Comparable<T>> implements HashTable<T> {

    private boolean deleted[];
    private final T nil = null;
    private int size;
    private int nElems;
    private T[] hashElems;
    private final double correctLoadFactor = 0.75;

    HashOpenAdressing() {
        this(2039); // initial size as random prime number
    }

    HashOpenAdressing(int size) {
        validateHashInitSize(size);
        this.size = size;
        this.hashElems = (T[]) new Comparable[this.size];
        deleted = new boolean[size];
    }

    @Override
    public void put(T newElem) {
        validateInputElem(newElem);
        resizeIfNeeded();
        int key = newElem.hashCode();
        int i = 0;
        int hashId = hashFunc(key, i);
        while (hashElems[hashId] != nil && deleted[hashId] != true && i != size) {
            if (hashElems[hashId].compareTo(newElem) == 0) {
                return;
            }
            hashId = hashFunc(key, i);
            i++;
        }
        if (i == size) {
            doubleResize();
            put(newElem);
        } else {
            hashElems[hashId] = newElem;
            nElems++;
        }
    }

    @Override
    public T get(T elem) {
        validateInputElem(elem);
        int key = elem.hashCode();
        int i = 0;
        int hashId = hashFunc(key, i);
        while (deleted[hashId] == true || (hashElems[hashId] != nil
                && hashElems[hashId].compareTo(elem) != 0)) {
            i++;
            hashId = hashFunc(key, i);
        }
        if (hashElems[hashId] != nil) {
            return hashElems[hashId];
        } else {
            return null;
        }
    }

    @Override
    public void delete(T elem) {
        validateInputElem(elem);
        int key = elem.hashCode();
        int i = 0;
        int hashId = hashFunc(key, i);
        while (deleted[hashId] == true || (hashElems[hashId] != nil
                && hashElems[hashId].compareTo(elem) != 0)) {
            i++;
            hashId = hashFunc(key, i);
        }
        if (hashElems[hashId] != nil) {
            hashElems[hashId] = null;
            deleted[hashId] = true;
            nElems--;
        }
    }

    private void validateHashInitSize(int initialSize) {
        if (initialSize < 1) {
            throw new IllegalArgumentException("Initial size of hash table cannot be lower than 1!");
        }
    }

    private void validateInputElem(T newElem) {
        if (newElem == null) {
            throw new IllegalArgumentException("Input elem cannot be null!");
        }
    }

    abstract int hashFunc(int key, int i);

    protected int getSize() {
        return size;
    }

    private void resizeIfNeeded() {
        double loadFactor = (double) nElems / (double) size;
        if (loadFactor >= correctLoadFactor) {
            doubleResize();
        }
    }

    private void doubleResize() {
        this.size *= 2;
        boolean[] old = deleted;
        deleted = new boolean[size];
        System.arraycopy(old, 0, deleted, 0, old.length);
        T[] newHashElems = (T[]) new Comparable[this.size];
        T[] oldHashElems = hashElems;
        hashElems = newHashElems;
        nElems = 0;
        for (int i = 0; i < oldHashElems.length; i++) {
            if (oldHashElems[i] != nil) {
                put(oldHashElems[i]);
            }
        }
    }
}
