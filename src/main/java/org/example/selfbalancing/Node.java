package org.example.selfbalancing;

public class Node<T extends Comparable<T>> {
    private T value;
    private Node<T> leftChild, rightChild;

    private int depth;

    Node(T value, Node<T> leftChild, Node<T> rightChild, int depth) {
        this.value = value;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.depth = depth;
    }

    public T getValue() {
        return value;
    }

    public Node<T> getLeftChild() {
        return leftChild;
    }

    public Node<T> getRightChild() {
        return rightChild;
    }

    public int getDepth() {
        return depth;
    }

    public void setLeftChild(Node<T> leftChild) {
        this.leftChild = leftChild;
    }

    public void setRightChild(Node<T> rightChild) {
        this.rightChild = rightChild;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void exchangeDataWith(Node<T> node) {
        T temp = node.value;
        node.value = value;
        value = temp;
    }

    @Override
    public String toString() {
        return "value: " + value;
    }
}
