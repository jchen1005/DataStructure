package com.datastructure.priorityqueues;

import java.util.*;

/**
 * Min heap Priority Queue
 *
 * @param <T>
 */
public class PriorityQueue<T extends Comparable<T>> {
    private List<T> heap = null;
    private Map<T, TreeSet<Integer>> lookUp = new HashMap<>();

    public PriorityQueue() {
        this(1);
    }

    public PriorityQueue(int heapCapacity) {
        heap = new ArrayList<>(heapCapacity);
    }

    // construct a priority queue o(nlogn)
    public PriorityQueue(Collection<T> elem) {
        this(elem.size());
        for(T ele:elem) add(ele);
    }
    public T poll() {
        return removeAt(0);
    }

    public T peek() {
        if (isEmpty()) return null;
        return heap.get(0);
    }

    public int size() {
        return heap.size();
    }

    public boolean isEmpty() {
        return heap.size() == 0;
    }

    public boolean contains(T elem) {
        if (elem == null) return false;
        return lookUp.containsKey(elem);
    }

    private boolean less(int i, int j) {
        T node1 = heap.get(i);
        T node2 = heap.get(j);
        return node1.compareTo(node2) <= 0;
    }

    public void add(T elem) {
        if (elem == null) throw new IllegalArgumentException();
        heap.add(elem);
        int lastIndex = size() - 1;
        mapAdd(elem, lastIndex);
        swim(lastIndex);
    }


    //O(n) remove
    public boolean remove(T elem){
        if(elem==null)return false;
      /*  for(int i=0; i<size();i++){
            if(elem.equals(heap.get(i))){
                removeAt(i);
                return true;
            }
        }*/

        //o(logn)
        Integer index=null;
        TreeSet<Integer> set = lookUp.get(elem);
        if(set!=null){
             index= set.last();
            if(index!=null){
                removeAt(index);
            }
        }
        return index!=null;

    }


    //bubble up
    private void swim(int k) {
        int parent = (k - 1) / 2;
        while (k > 0 && less(k, parent)) {
            swap(parent, k);
            k = parent;
            parent = (k - 1) / 2;
        }
    }

    //bubble down
    private void sink(int k) {
        int heapSize = size();
        while (true) {
            int left = 2 * k + 1;
            int right = 2 * k + 2;
            int smallest = left;
            if (right < heapSize && less(right, left)) {
                smallest = right;
            }
            //there is no child node
            if (left >= heapSize || less(k, smallest)) {
                break;
            }
            swap(k, smallest);
            //keep bubbling down the tree
            k = smallest;
        }
    }

    private T removeAt(int index) {
        if (isEmpty()) return null;
        int lastIndex = size() - 1;
        T removedData = heap.get(index);
        swap(index, lastIndex);
        heap.remove(lastIndex);
        mapRemove(removedData,index);

        // Removed last element
        if (index == lastIndex) return removedData;

        T elem=heap.get(index);

        //try bubble down
        sink(index);
        //try bubble up if sinking does not work
        //this happens when the removed node is in the bottom of the tree
        if(heap.get(index).equals(elem)) swim(index);

        return removedData;
    }

    private void swap(int i, int j) {
        T node1 = heap.get(i);
        T node2 = heap.get(j);
        heap.set(i, node2);
        heap.set(j, node1);
        mapSwap(node1, node2, i, j);
    }

    /**
     * Add the element to lookUp map
     *
     * @param elem
     * @param index
     */
    private void mapAdd(T elem, int index) {
        TreeSet<Integer> set = lookUp.get(elem);
        if (set == null) {
            set = new TreeSet<>();
            set.add(index);
            lookUp.put(elem, set);
        } else {
            set.add(index);
        }

    }

    //swap the index of lookUp Map
    private void mapSwap(T node1, T node2, int i, int j) {
        Set<Integer> set1 = lookUp.get(node1);
        Set<Integer> set2 = lookUp.get(node1);
        set1.remove(i);
        set2.remove(j);
        set1.add(j);
        set2.add(i);
    }

    private void mapRemove(T removeData ,int index){
        TreeSet<Integer> set = lookUp.get(removeData);
        set.remove(index);
        if(set.size()==0) lookUp.remove(removeData);
    }
}
