package org.example.selfbalancing;

class Interval implements Comparable<Interval> {
    private final int low, high;
    private int maxHighInRooted;

    Interval(int low, int high, int maxHighInRooted) {
        this.low = low;
        this.high = high;
        this.maxHighInRooted = maxHighInRooted;
    }

    public int getLow() {
        return low;
    }

    public int getHigh() {
        return high;
    }

    public int getMaxHighInRooted() {
        return maxHighInRooted;
    }

    public void setMaxHighInRooted(int maxHighInRooted) {
        this.maxHighInRooted = maxHighInRooted;
    }

    @Override
    public int compareTo(Interval interval) {
        return Integer.compare(low, interval.low);
    }

    @Override
    public String toString() {
        return "(" + low + ", " + high + ")";
    }
}

public class IntervalTree extends AVLTree<Interval> {
    public IntervalTree() {
        setOnBack(node -> {
            int maxHighInRooted = node.getValue().getHigh();

            if(node.getLeftChild() != null) {
                maxHighInRooted = Math.max(maxHighInRooted, node.getLeftChild().getValue().getMaxHighInRooted());
            }

            if(node.getRightChild() != null) {
                maxHighInRooted = Math.max(maxHighInRooted, node.getRightChild().getValue().getMaxHighInRooted());
            }

            node.getValue().setMaxHighInRooted(maxHighInRooted);
        });
    }

    public void insert(int low, int high) {
        super.insert(new Interval(low, high, high));
    }

    public boolean exists(int low, int high) {
        Node<Interval> curr = root;

        while(curr != null) {
            if(high >= curr.getValue().getLow() && low <= curr.getValue().getHigh()) {
                break;
            }

            if(curr.getLeftChild() != null && low <= curr.getLeftChild().getValue().getMaxHighInRooted()) {
                curr = curr.getLeftChild();
            }
            else {
                curr = curr.getRightChild();
            }
        }

        return (curr != null);
    }
}