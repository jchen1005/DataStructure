package com.datastructure.queues;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Queue implementation using Doubled Linklist
 */
public class LinkedQueue<T> implements Iterable<T> {
    private LinkedList<T> list = new LinkedList<>();

    public LinkedQueue() {
    }

    public LinkedQueue(T firstElem) {
        offer(firstElem);
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    //Return first element of the queue without removing it
    public T peek() {
        if (isEmpty()) throw new RuntimeException("Empty Queue");
        return list.peekFirst();
    }

    //remove an element from the front of the queue
    public T poll() {
        if (isEmpty()) throw new RuntimeException("Empty Queue");
        return list.removeFirst();
    }

    // add an element to the back of the queue
    public void offer(T elem) {
      list.addLast(elem);
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }
}
