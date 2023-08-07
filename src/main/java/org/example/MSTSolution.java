package org.example;

import java.util.HashSet;
import java.util.PriorityQueue;

public class MSTSolution {
    static class Edge implements Comparable<Edge> {
        int to;
        int weight;

        public Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge edge) {
            return Integer.compare(weight, edge.weight);
        }
    }

    public int minCostConnectPoints(int[][] points) {
        // construct graph
        int n = points.length;

        int[][] graph = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int dist = dist(points[i], points[j]);
                graph[i][j] = dist;
                graph[j][i] = dist;
            }
        }

        // find MST. Vertices from 0 to n - 1
        int weight = 0;

        PriorityQueue<Edge> queue = new PriorityQueue<>();
        HashSet<Integer> unvisited = new HashSet<>();
        queue.add(new Edge(0, 0));

        for(int i=0;i<=n-1;i++) {
            unvisited.add(i);
        }

        while(!queue.isEmpty()) {
            Edge smallestWeightEdge = queue.remove();

            if(!unvisited.contains(smallestWeightEdge.to)) {
                continue;
            }

            unvisited.remove(smallestWeightEdge.to);
            weight += smallestWeightEdge.weight;

            for(int unvisitedNode : unvisited) {
                queue.add(new Edge(unvisitedNode, graph[unvisitedNode][smallestWeightEdge.to]));
            }
        }

        return weight;
    }

    private int dist(int[] cord1, int[] cord2) {
        return Math.abs(cord1[0] - cord2[0]) + Math.abs(cord1[1] - cord2[1]);
    }
}

