package pl.edu.pw.ee;

import pl.edu.pw.ee.services.HashTable;

public class HashListChaining<T extends Comparable<T>> implements HashTable<T> {

    private final Elem<T> nil = null;
    private Elem[] hashElems;
    private int nElem;

    private class Elem<T> {

        private T value;
        private Elem next;

        Elem(T value, Elem nextElem) {
            this.value = value;
            this.next = nextElem;
        }
    }

    public HashListChaining(int size) {
        if (size < 1) {
            throw new IllegalArgumentException("Hash size has to be positive");
        }
        hashElems = new Elem[size];
        initializeHash();
    }

    @Override
    public void add(T value) {
        if (value == null) {
            throw new IllegalArgumentException("Value to be added cannot be null");
        }
        int hashCode = value.hashCode();
        int hashId = countHashId(hashCode);

        Elem<T> oldElem = hashElems[hashId];
        while (oldElem != nil && !oldElem.value.equals(value)) {
            oldElem = oldElem.next;
        }
        if (oldElem != nil && oldElem.value.equals(value)) {
            throw new UnsupportedOperationException("Value to be added is already present");
        }
        hashElems[hashId] = new Elem<T>(value, hashElems[hashId]);
        nElem++;
    }

    @Override
    public T get(T value) {
        if (value == null) {
            throw new IllegalArgumentException("Value to be retrieved cannot be null");
        }
        return getOrDelete(value, false);
    }

    @Override
    public void delete(T value) {
        if (value == null) {
            throw new IllegalArgumentException("Value to be deleted cannot be null");
        }
        getOrDelete(value, true);
    }

    private T getOrDelete(T value, boolean shouldDelete) {
        int hashCode = value.hashCode();
        int hashId = countHashId(hashCode);

        Elem<T> elem = hashElems[hashId];
        Elem prevElem = null;
        while (elem != nil && !elem.value.equals(value)) {
            prevElem = elem;
            elem = elem.next;
        }
        if (shouldDelete) {
            if (prevElem != null && elem != nil) {
                prevElem.next = elem.next;
                nElem--;
            } else if (prevElem != null) {
                prevElem = nil;
                nElem--;
            } else if (prevElem == null && elem.value.equals(value)) {
                hashElems[hashId] = elem.next;
                nElem--;
            }
        }
        return elem != nil ? elem.value : null;
    }

    public double countLoadFactor() {
        double size = hashElems.length;
        return nElem / size;
    }

    private void initializeHash() {
        int n = hashElems.length;

        for (int i = 0; i < n; i++) {
            hashElems[i] = nil;
        }
    }

    private int countHashId(int hashCode) {
        int n = hashElems.length;
        if (hashCode == Integer.MIN_VALUE) {
            hashCode++;
        }
        return Math.abs(hashCode) % n;
    }

}
