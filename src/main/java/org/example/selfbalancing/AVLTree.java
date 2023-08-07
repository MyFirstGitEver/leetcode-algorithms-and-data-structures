package org.example.selfbalancing;

import org.example.selfbalancing.Node;

import java.util.ArrayList;
import java.util.List;

public class AVLTree<T extends Comparable<T>> {
    protected Node<T> root;

    private OnRecursionBack<T> onBack = null;

    private int size;

    public AVLTree(T[] arr) {
        if (arr.length == 0) {
            root = null;
            size = 0;
            return;
        }

        root = new Node<>(arr[0], null, null, 0);

        for (int i = 1; i < arr.length; i++) {
            insert(arr[i]);
        }

        size = arr.length;
    }

    public AVLTree() {
        root = null;
        size = 0;
    }

    public void insert(T data) {
        if(root == null) {
            root = new Node<>(data, null, null, 0);
            return;
        }

        insertRecursive(data, root);
        size++;
    }

    public List<T> sortedOrder() {
        List<T> sorted = new ArrayList<>(size);
        sortedOrderRecursive(sorted, root);

        return sorted;
    }

    public int size() {
        return size;
    }

    protected void setOnBack(OnRecursionBack<T> onBack) {
        this.onBack = onBack;
    }

    private void sortedOrderRecursive(List<T> sorted, Node<T> curr) {
        if (curr == null) {
            return;
        }

        sortedOrderRecursive(sorted, curr.getLeftChild());
        sorted.add(curr.getValue());
        sortedOrderRecursive(sorted, curr.getRightChild());
    }

    private Node<T> insertRecursive(T data, Node<T> curr) {
        if (curr == null) {
            return new Node<>(data, null, null, 0);
        }

        Node<T> changedChild;

        if (data.compareTo(curr.getValue()) <= 0) {
            changedChild = insertRecursive(data, curr.getLeftChild());
            curr.setLeftChild(changedChild);
        }
        else {
            changedChild = insertRecursive(data, curr.getRightChild());
            curr.setRightChild(changedChild);
        }

        int factor = balanceFactor(curr);

        if (factor == 2 || factor == -2) {
            balance(curr);
        }
        else {
            updateDepth(curr);
        }

        return curr;
    }



    private void balance(Node<T> z) {
        Node<T> x, y;

        y = nextChild(z);
        x = nextChild(y);

        if (y == z.getLeftChild() && x == y.getLeftChild()) {
            // LL
            z.exchangeDataWith(y);
            leftTransform(y, z);
        } else if (y == z.getLeftChild() && x == y.getRightChild()) {
            // LR

            y.setRightChild(x.getLeftChild());
            x.setLeftChild(y);
            z.setLeftChild(x);

            z.exchangeDataWith(x);
            leftTransform(x, z);
        } else if (y == z.getRightChild() && x == y.getLeftChild()) {
            // RL

            y.setLeftChild(x.getRightChild());
            x.setRightChild(y);
            z.setRightChild(x);

            z.exchangeDataWith(x);
            rightTransform(x, z);
        } else {
            // RR
            z.exchangeDataWith(y);
            rightTransform(y, z);
        }

        updateDepth(x);
        updateDepth(y);
        updateDepth(z);
    }

    private void leftTransform(Node<T> zReal, Node<T> yReal) {
        Node<T> C = zReal.getRightChild();
        Node<T> X = zReal.getLeftChild();

        zReal.setRightChild(yReal.getRightChild());
        zReal.setLeftChild(C);

        yReal.setLeftChild(X);
        yReal.setRightChild(zReal);
    }

    private void rightTransform(Node<T> zReal, Node<T> yReal) {
        Node<T> B = zReal.getLeftChild();
        Node<T> X = zReal.getRightChild();

        zReal.setLeftChild(yReal.getLeftChild());
        zReal.setRightChild(B);

        yReal.setRightChild(X);
        yReal.setLeftChild(zReal);
    }

    private int childDepth(Node<T> node, boolean leftChild) {
        // Node is guaranteed to be non-null!

        if (leftChild) {
            return node.getLeftChild() == null ? -1 : node.getLeftChild().getDepth();
        }

        return node.getRightChild() == null ? -1 : node.getRightChild().getDepth();
    }

    private void updateDepth(Node<T> node) {
        int leftDepth = childDepth(node, true);
        int rightDepth = childDepth(node, true);

        node.setDepth(Math.max(leftDepth, rightDepth) + 1);

        if(onBack != null) {
            onBack.onBack(node);
        }
    }

    private Node<T> nextChild(Node<T> node) {
        int lDepth = childDepth(node, true);
        int rDepth = childDepth(node, false);

        if (lDepth > rDepth) {
            return node.getLeftChild();
        } else {
            return node.getRightChild();
        }
    }

    private int balanceFactor(Node<T> n) {
        int leftDepth = -1, rightDepth = -1;

        if (n.getLeftChild() != null) {
            leftDepth = n.getLeftChild().getDepth();
        }

        if (n.getRightChild() != null) {
            rightDepth = n.getRightChild().getDepth();
        }

        return leftDepth - rightDepth;
    }
}