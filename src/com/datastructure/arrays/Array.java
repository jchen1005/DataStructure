package com.datastructure.arrays;

import java.util.ArrayList;
import java.util.Iterator;

public class Array<T> implements Iterable<T> {
    private T[] arr;
    private int len = 0;
    private int capacity = 0;

    public Array() {
        this(16);
    }

    public Array(int capacity) {
        if (capacity < 0) throw new IllegalArgumentException("Illegal Capacity" + capacity);
        this.capacity = capacity;
        arr = (T[]) new Object[capacity];
    }

    public int size() {
        return len;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public T get(int index) {
        return arr[index];
    }

    public void set(int index, T value) {
        arr[index] = value;
    }

    public void clear() {
        for (int i = 0; i < len; i++) {
            arr[i] = null;
            len = 0;
        }
    }

    public void add(T elem) {
        if (len + 1 > capacity) {
            if (capacity == 0)
                capacity = 1;
            else capacity = capacity * 2;//Double the size of initial array
            T[] newArray = (T[]) new Object[capacity];
            for (int i = 0; i < len; i++) {
                newArray[i] = arr[i];
            }
            arr = newArray;
        }
        arr[len++] = elem;
    }

    public T removeAt(int removeIndex) {
        if (removeIndex >= len || removeIndex < 0) throw new IndexOutOfBoundsException("");
        T data = arr[removeIndex];
        T[] newArray = (T[]) new Object[len - 1];
        for (int i = 0, j = 0; i < len; i++, j++) {
            if (i == removeIndex) j--;
            else newArray[j] = arr[i];
        }
        arr = newArray;
        capacity = --len;
        return data;
    }

    public boolean remove(Object obj) {
        for (int i = 0; i < len; i++) {
            if (arr[i].equals(obj)) {
                removeAt(i);
                return true;
            }
        }
        return false;
    }

    public int indexOf(Object obj) {
        for (int i = 0; i < len; i++) {
            if (arr[i].equals(obj)) {
                return i;
            }
        }
        return -1;
    }

    public boolean contains(Object obj) {
        return indexOf(obj) != -1;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int index=0;
            @Override
            public boolean hasNext() {
                return index<len;
            }
            @Override
            public T next() {
                return arr[index++];
            }
        };
    }

    @Override
    public String toString() {

        new ArrayList<>();
        if (len == 0) return "[]";
        else {
            StringBuilder sb = new StringBuilder(len).append("[");
            for (int i = 0; i < len - 1; i++) sb.append(arr[i] + ", ");
            return sb.append(arr[len - 1] + "]").toString();
        }
    }
}
