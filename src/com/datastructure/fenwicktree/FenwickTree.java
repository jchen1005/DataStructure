package com.datastructure.fenwicktree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class FenwickTree {

    private long[] tree;

    public FenwickTree(int sz) {
        if (sz == 0) throw new IllegalArgumentException();
        tree = new long[sz + 1];
    }

    public FenwickTree(long[] values) {
        this(values.length);
        //clone the values array
//        this.tree = values.clone();

        for (int i = 1; i <= values.length; i++) {
            tree[i] = 0;
        }
        System.out.println(Arrays.toString(tree));
        for (int i = 0; i < values.length; i++) {

            updateTreeIndex(i, values.length, values[i]);
           /* int j = i + lsb(i);
            if (j < tree.length) tree[j] += tree[i];*/
        }
        System.out.println(Arrays.toString(this.tree));


    }

    private void updateTreeIndex(int index, int n, long value) {
        index = index + 1;
        while (index <= n) {
            tree[index] += value;
            index = index + lsb(index);
        }
    }

    private int lsb(int i) {
        //bit manipulation , reduce the machine level calculation
//        return i & -i;
        return Integer.lowestOneBit(i);
    }

    // compute the prefix sum from [1,i]
    public long prefixSum(int i) {
        long sum = 0L;
        i = i + 1;
        while (i!= 0) {
            sum += tree[i];
            //do down the tree based on the level of responsibility
            i -= lsb(i);
        }
        return sum;
    }

    public long rangeSum(int i, int j) {
        if (i > j) throw new IllegalArgumentException();
        return prefixSum(j) - prefixSum(i - 1);
    }


    //add k to index i
    public void add(int i, long k) {
        i=i+1;
        while (i < tree.length) {
            tree[i] += k;
            i += lsb(i);
        }
    }


    public void rangeUpdate(int i, long k) {
        //find the sum of current index
        long currentSum = rangeSum(i, i);
        //update the node and its parent node with the difference
        add(i, k - currentSum);
    }

    public String toString() {
        return Arrays.toString(tree);
    }
}

 class Test {

    public static void main(String[] args) {
        //first index is not used
        long[] input = {5, 1, 2, 3, 4, 5};

        FenwickTree tree = new FenwickTree(input);

        long sum = tree.rangeSum(0, 5);
        tree.add(1,3);
        System.out.println(sum);

        long sum2 = tree.rangeSum(0, 5);

        System.out.println(sum2);

    }
}
