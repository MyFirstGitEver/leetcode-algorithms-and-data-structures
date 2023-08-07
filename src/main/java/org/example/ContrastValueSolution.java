package org.example;

public class ContrastValueSolution {
    public int solve(int n, int[] arr) {
        if(n == 1) {
            return 1;
        }

        int ptr = 1, answer = 0, monotoneNum = 0;
        long lastTendency = 0;

        while(ptr < n) {
            int lastPtr = ptr - 1;

            while(ptr < n && lastTendency * (arr[ptr] - arr[ptr - 1]) >= 0) {
                lastTendency = currentTendency(lastTendency, arr[ptr - 1], arr[ptr]);
                ptr++;
            }

            int cnt = ptr - lastPtr;

            if(arr[lastPtr] == arr[ptr - 1]) {
                answer++;
            }
            else {
                answer += Math.min(2, cnt);
            }

            lastTendency = 0;
            monotoneNum++;
        }

        return answer - (monotoneNum - 1);
    }

    static long currentTendency(long last, int first, int second) {
        if(last != 0) {
            return last;
        }

        return (long) second - first;
    }

    static void driver() {
        int t;

        Main.FastReader reader = new Main.FastReader();

        t = reader.nextInt();

        for(int i=0;i<t;i++) {
            int n = reader.nextInt();

            int[] arr = new int[n];

            for(int j=0;j<n;j++) {
                arr[j] = reader.nextInt();
            }

            ContrastValueSolution solution = new ContrastValueSolution();

            System.out.println(solution.solve(n, arr));

        }
    }
}
