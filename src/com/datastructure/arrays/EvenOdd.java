package com.datastructure.arrays;

import java.util.*;

public class EvenOdd {
    public static void main(String[] args) {
        List<Integer> integers = Arrays.asList(1, 3, 2, 3);
        HashMap<String, String> map = new HashMap<>();
        map.put(null, "abc");
        map.put(null, "cds");
        System.out.println(map);

        Hashtable<String, String> table = new Hashtable<>();
        table.put(null, "adsa");
//        evenOdd(integers);

    }

    private static void evenOdd(List<Integer> A) {
        int nextEven = 0, nextOdd = A.size() - 1;
        while (nextEven < nextOdd) {
            if (A.get(nextEven) % 2 == 0) {
                nextEven++;
            } else {
                Collections.swap(A, nextEven, nextOdd--);
                System.out.println(A.toString());
            }
        }

    }
}
