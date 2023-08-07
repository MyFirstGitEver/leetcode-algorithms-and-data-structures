package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UniqueBSTSolution {
    public List<TreeNode> generateTrees(int n) {
        int[] unused = new int[n];

        for(int i=1;i<=n;i++) {
            unused[i - 1] = i;
        }

        return threeCases(unused);
    }

    private List<TreeNode> threeCases(int[] unused) {
        if(unused.length == 0) {
            return new ArrayList<>();
        }
        else if(unused.length == 1) {
            List<TreeNode> result = new ArrayList<>();
            result.add(new TreeNode(unused[0]));

            return result;
        }

        List<TreeNode> result = new ArrayList<>();

        for(int i=0;i<unused.length;i++) {
            int[] leftUnused = Arrays.copyOfRange(unused, 0, i);
            int[] rightUnused = Arrays.copyOfRange(unused, i + 1, unused.length);

            List<TreeNode> leftSubtrees = threeCases(leftUnused);
            List<TreeNode> rightSubtrees = threeCases(rightUnused);

            if(leftSubtrees.size() == 0) {
                for(TreeNode rightSubtree : rightSubtrees) {
                    result.add(new TreeNode(unused[i], null, rightSubtree));
                }
            }
            else if(rightSubtrees.size() == 0) {
                for(TreeNode leftSubtree : leftSubtrees) {
                    result.add(new TreeNode(unused[i], leftSubtree, null));
                }
            }
            else {
                for(TreeNode leftSubtree : leftSubtrees) {
                    for(TreeNode rightSubtree: rightSubtrees) {
                        result.add(new TreeNode(unused[i], leftSubtree, rightSubtree));
                    }
                }
            }
        }

        return result;
    }

    private int[] removeAt(int i, int[] unused) {
        int[] result = new int[unused.length - 1];

        for(int index=0;index<i;index++) {
            result[index] = unused[index];
        }

        for(int index=i + 1;index<unused.length;index++) {
            result[index - 1] = unused[index];
        }

        return result;
    }
}
