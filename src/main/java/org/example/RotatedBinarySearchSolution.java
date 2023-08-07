package org.example;

public class RotatedBinarySearchSolution {
    public int search(int[] nums, int target) {
        int l = 0, r = nums.length - 1, m = 0;

        while (l <= r) {
            m = (l + r) / 2;

            // definition of pivot
            if (m + 1 < nums.length && nums[m] > nums[m + 1]) {
                break;
            }

            if (nums[m] < nums[l]) {
                r = m - 1;
            } else {
                l = m + 1;
            }
        }

        int leftResult = bs(nums, 0, m, target);
        int rightResult = bs(nums, m + 1, nums.length - 1, target);

        return Math.max(leftResult, rightResult);
    }

    private int bs(int[] arr, int from, int to, int target) {
        int l = from, r = to, m = 0;

        while (l <= r) {
            m = (l + r) / 2;

            if (arr[m] == target) {
                break;
            }

            if (arr[m] < target) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }

        return arr[m] == target ? m : -1;
    }

    private void driver() {
        int n;

        Main.FastReader reader = new Main.FastReader();
        n = reader.nextInt();

        int[] arr = new int[n];

        for(int i=0;i<arr.length;i++) {
            arr[i] = reader.nextInt();
        }

        int target = reader.nextInt();

        System.out.println(new RotatedBinarySearchSolution().search(arr, target));
    }
}
