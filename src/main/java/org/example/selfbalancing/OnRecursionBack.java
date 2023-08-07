package org.example.selfbalancing;

public interface OnRecursionBack<T extends Comparable<T>> {
    void onBack(Node<T> node);
}