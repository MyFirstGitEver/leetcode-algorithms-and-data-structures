package org.example;

import org.example.selfbalancing.IntervalTree;

class MyCalendar {
    private final IntervalTree tree;

    public MyCalendar() {
        tree = new IntervalTree();
    }

    public boolean book(int start, int end) {
        if(tree.exists(start, end - 1)) {
            return false;
        }

        tree.insert(start, end - 1);

        return true;
    }
}

public class SegmentTree {
    private final long[] sum;

    public SegmentTree(int[] data) {
        sum = new long[data.length * 2 - 1];

        build(data);
    }
    private void build(int[] data) {
        for(int i=0;i<data.length;i++) {
            sum[i + data.length - 1] = data[i];
        }

        for(int i=data.length - 2;i >= 0;i--) {
            sum[i] = sum[2 * i + 1] + sum[2 * i + 2];
        }
    }

    public void update(int index, int value) {
        int n = (sum.length + 1) / 2;

        int curr = index + n - 1;
        sum[curr] = value;

        while(curr > 0) {
            curr = (curr - 1) / 2;

            sum[curr] = sum[2 * curr + 1] + sum[2 * curr + 2];
            if(2 * curr + 2 == sum.length) {
                sum[curr] += sum[2 * curr + 2];
            }
        }
    }

    public long sum(int left, int right) {
        long total = 0;
        int n = (sum.length + 1) / 2;

        // start from the lowest level
        left += (n - 1);
        right += (n - 1);

        while (true){
            // regularize the two end points
            if(left % 2 == 0) {
                total += sum[left];
                left++;
            }

            if(right % 2 == 1) {
                total += sum[right];
                right--;
            }

            if(left > right) {
                break;
            }

            // convert this [left...right] of this level to the one of the upper level
            left = (left - 1) / 2;
            right =(right - 1) / 2;
        }

        return total;
    }
}
class NumArray {
    SegmentTree tree;

    public NumArray(int[] nums) {
        tree = new SegmentTree(nums);
    }

    public void update(int index, int val) {
        tree.update(index, val);
    }

    public int sumRange(int left, int right) {
        return (int) tree.sum(left, right);
    }
}
