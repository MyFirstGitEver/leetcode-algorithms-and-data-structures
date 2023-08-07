package org.example;

public class Pair<X extends Comparable<X>, Y> implements Comparable<Pair<X, Y>> {

    X first;
    Y second;

    public Pair(X first, Y second) {
        this.first = first;
        this.second = second;
    }

    public Pair() {
    }

    @Override
    public int compareTo(Pair<X, Y> xyPair) {
        return first.compareTo(xyPair.first);
    }
}
