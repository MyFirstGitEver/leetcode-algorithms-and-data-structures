package org.example;

public class ValidateBSTSolution {
    public boolean isValidBST(TreeNode root) {
        return checker(root) != null;
    }

    // return (min, max)
    public Pair<Long, Long> checker(TreeNode root) {
        if (root == null) {
            return new Pair<>(Long.MAX_VALUE, Long.MIN_VALUE);
        }

        Pair<Long, Long> minAndMaxLeft = checker(root.left);
        Pair<Long, Long> minAndMaxRight = checker(root.right);

        if (minAndMaxLeft == null || minAndMaxRight == null) {
            return null;
        }

        long maxLeft = minAndMaxLeft.second;
        long minRight = minAndMaxRight.first;

        if (root.val <= maxLeft || root.val >= minRight) {
            return null;
        }

        long currentMin = Math.min(root.val, Math.min(minRight, minAndMaxLeft.first));
        long currentMax = Math.max(root.val, Math.min(maxLeft, minAndMaxRight.second));

        return new Pair<>(currentMin, currentMax);
    }
}
