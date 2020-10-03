package com.datastructure.binarysearchtree;

import java.util.*;

public class BinarySearchTree<T extends Comparable<T>> {
    private int nodeCount = 0;
    private Node root = null;

    private class Node {
        T dada;
        Node left, right;

        public Node(Node left, Node right, T elem) {
            this.left = left;
            this.right = right;
            this.dada = elem;
        }
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return nodeCount;
    }

    public boolean add(T elem) {
        if (contains(elem)) {
            return false;
        } else {
            root = add(root, elem);
            nodeCount++;
            return true;
        }
    }

    private Node add(Node node, T elem) {
        if (node == null) {
            node = new Node(null, null, elem);
        } else {
            if (elem.compareTo(node.dada) < 0) {
                node.left = add(node.left, elem);
            } else {
                node.right = add(node.right, elem);
            }
        }
        return node;
    }

    public boolean remove(T elem) {
        if (contains(elem)) {
            root = remove(root, elem);
            nodeCount--;
            return true;
        }
        return false;
    }

    private Node remove(Node node, T elem) {
        if (node == null) return null;
        int comp = elem.compareTo(node.dada);
        if (comp < 0) {
            node.left = remove(node.left, elem);
        } else if (comp > 0) {
            node.right = remove(node.right, elem);
        } else {
            //found the element
            // node has only right subtree
            if (node.left == null) {
                Node rightNode = node.right;
                node.dada = null;
                node = null;
                return rightNode;

            }
            //node has only right subtree
            else if (node.right == null) {
                Node leftNode = node.left;
                node.dada = null;
                node = null;
                return leftNode;
            } else {
                //node has both left and right child
                //start from the right
                Node smallest = findMin(node.right);
                //swap the element
                node.dada = smallest.dada;
                node.right = remove(node.right, smallest.dada);

                //or grab the largest Node from the left substree

                Node largestLeft = digRight(node.left);

                node.dada = largestLeft.dada;

                node.left = remove(node.left, largestLeft.dada);
            }

        }

        return node;


    }

    //find the smallest element in the right subtree
    public Node findMin(Node node) {
        Node cur = node;
        while (cur.left != null)
            cur = cur.left;
        return cur;
    }

    public Node digRight(Node node) {
        Node cur = node;
        while (cur.right != null)
            cur = cur.right;
        return cur;
    }

    private boolean contains(T elem) {
        return contains(root, elem);
    }

    private boolean contains(Node node, T elem) {
        // Base case
        if (node == null) return false;
        //compare to node
        int comp = elem.compareTo(node.dada);
        //element is less than the node, search in the left subtree
        if (comp < 0) {
            return contains(node.left, elem);
            // search in the right subtree
        } else if (comp > 0) {
            return contains(node.right, elem);
        } else {
            //element found
            return true;
        }
    }

    public int height() {
        return height(root);
    }

    private int height(Node node) {
        if (node == null) return 0;
        return Math.max(height(node.left), height(node.right)) + 1;
    }


    private Iterator<T> preOrderTraversal() {
        final int expectedNodeCount = nodeCount;
        final Stack<Node> stack = new Stack<>();
        stack.push(root);
        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                if (expectedNodeCount != nodeCount) throw new ConcurrentModificationException();
                return root != null && !stack.isEmpty();
            }

            @Override
            public T next() {
                if (expectedNodeCount != nodeCount) throw new ConcurrentModificationException();
                stack.pop();
                if (root.right != null) stack.push(root.right);
                if (root.left != null) stack.push(root.left);
                return root.dada;
            }
        };
    }

    private Iterator<T> inOrderTraversal() {
        final int expectedNodeCount = nodeCount;
        final Stack<Node> stack = new Stack<>();
        stack.push(root);
        return new Iterator<T>() {
            Node trav = root;

            @Override
            public boolean hasNext() {
                if (expectedNodeCount != nodeCount) throw new ConcurrentModificationException();
                return root != null && !stack.isEmpty();
            }

            @Override
            public T next() {
                if (expectedNodeCount != nodeCount) throw new ConcurrentModificationException();

                //dig left
                while (trav != null && trav.left != null) {
                    stack.push(trav.left);
                    trav = trav.left;
                }
                Node node = stack.pop();
                // Try moving down right once
                if (node.right != null) {
                    stack.push(node.right);
                    trav = node.right;
                }
                return node.dada;
            }
        };
    }

    private Iterator<T> postOrderTraversal() {
        final int expectedNodeCount = nodeCount;
        final Stack<Node> stack1 = new Stack<>();
        final Stack<Node> stack2 = new Stack<>();
        stack1.push(root);
        while (!stack1.isEmpty()) {
            Node node = stack1.pop();
            if (node != null) {
                stack2.push(node);
                if (node.left != null)
                    stack1.push(node.left);
                if (node.right != null)
                    stack1.push(node.right);
            }
        }

        return new Iterator<T>() {

            @Override
            public boolean hasNext() {
                if (expectedNodeCount != nodeCount) throw new ConcurrentModificationException();
                return root != null && !stack2.isEmpty();
            }

            @Override
            public T next() {
                if (expectedNodeCount != nodeCount) throw new ConcurrentModificationException();
                return stack2.pop().dada;
            }
        };
    }

    private java.util.Iterator<T> levelOrderTraversal() {
        final Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return root != null && !queue.isEmpty();
            }

            @Override
            public T next() {
                //remove the first element in the queue
                Node node = queue.poll();
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
                return node.dada;
            }
        };
    }
}
