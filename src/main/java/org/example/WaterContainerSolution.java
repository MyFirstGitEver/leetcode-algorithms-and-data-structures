package org.example;

import java.util.Arrays;

public class WaterContainerSolution {
    public int maxArea(int[] height) {
        Pair<Integer, Integer>[] pairs = new Pair[height.length];

        for(int i=0;i<height.length;i++) {
            pairs[i] = new Pair<>(height[i], i);
        }

        Arrays.sort(pairs);

        Pair<Integer, Integer>[] maxAndMin = new Pair[height.length];
        maxAndMin[maxAndMin.length - 1] = new Pair<>(pairs[pairs.length - 1].second, pairs[pairs.length - 1].second);

        for(int i=maxAndMin.length - 2;i>=0;i--) {
            maxAndMin[i] = new Pair<>();
            maxAndMin[i].first = Math.max(maxAndMin[i + 1].first, pairs[i].second);
            maxAndMin[i].second = Math.min(maxAndMin[i + 1].second, pairs[i].second);
        }

        int answer = Integer.MIN_VALUE;

        for(int i=0;i<height.length-1;i++) {
            int max = maxAndMin[i].first;
            int min = maxAndMin[i].second;

            int factor = Math.max(Math.abs(pairs[i].second - max), Math.abs(pairs[i].second - min));

            answer = Math.max(answer, pairs[i].first * factor);
        }

        return answer;
    }

    static void driver() {
        Main.FastReader reader = new Main.FastReader();

        int n = reader.nextInt();

        int[] arr = new int[n];

        for(int i=0;i<n;i++) {
            arr[i] = reader.nextInt();
        }

        System.out.println(new WaterContainerSolution().maxArea(arr));
    }
}