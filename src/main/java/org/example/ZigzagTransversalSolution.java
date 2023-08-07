package org.example;

import java.util.*;
import java.util.LinkedList;

class BfsObj {
    TreeNode node;
    int depth;

    public BfsObj(TreeNode node, int depth) {
        this.node = node;
        this.depth = depth;
    }
}


class ZigzagTransversalSolution {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        Queue<BfsObj> q = new LinkedList<>();
        List<List<Integer>> answer = new ArrayList<>();

        q.add(new BfsObj(root, 0));

        while(q.size() != 0) {
            BfsObj current = q.remove();

            if(current.node == null) {
                continue;
            }

            if(current.depth == answer.size()) {
                answer.add(new ArrayList<>());
            }

            answer.get(current.depth).add(current.node.val);
            q.add(new BfsObj(current.node.left, current.depth + 1));
            q.add(new BfsObj(current.node.right, current.depth + 1));
        }

        for(int i=1;i<answer.size();i+= 2) {
            Collections.reverse(answer.get(i));
        }

        return answer;
    }
}