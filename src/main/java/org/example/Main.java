package org.example;

import org.example.selfbalancing.IntervalTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.*;

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

class SegmentTree {
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

class BoardContainer2 {
    char[][] board;
    int code;

    public BoardContainer2(char[][] board) {
        this.board = board;
        code = Arrays.deepHashCode(board);
    }

    @Override
    public boolean equals(Object obj) {
        BoardContainer2 container = (BoardContainer2) obj;

        int n = container.board.length;

        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                if(container.board[i][j] != board[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        return code;
    }
}

class Solution {
    private final List<List<String>> answer = new ArrayList<>(10_000);
    private final HashSet<BoardContainer2> visited = new HashSet<>();

    public List<List<String>> solveNQueens(int n) {
        char[][] initialState = new char[n][n];

        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                initialState[i][j] = '.';
            }
        }

        dfs(0, initialState);

        return answer;
    }

    private void dfs(int queenCount, char[][] boardContent) {
        int n = boardContent.length;

        if(queenCount == n) {
            answer.add(drawBoardFromChars(boardContent));
            return;
        }

        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                if(boardContent[i][j] == '.') {
                    char[][] newSetting = incrementBoardAt(i, j, boardContent);
                    BoardContainer2 container = new BoardContainer2(newSetting);

                    // if this setting is valid and have not seen it before
                    if(!visited.contains(container)) {
                        visited.add(container); // seen it already
                        dfs(queenCount + 1, newSetting);
                    }
                }
            }
        }
    }

    private char[][] incrementBoardAt(int x, int y, char[][] boardContent) {
        int n = boardContent.length;
        char[][] newBoard = new char[n][n];

        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                newBoard[i][j] = boardContent[i][j];
            }
        }

        banSquares(newBoard, x, y);

        newBoard[x][y] = 'Q';
        return newBoard;
    }

    private List<String> drawBoardFromChars(char[][] board) {
        int n = board.length;

        List<String> drawnBoard = new ArrayList<>(n);

        for (char[] row : board) {
            // board[i] -> String

            char[] newRow = new char[row.length];

            for(int i=0;i<n;i++) {
                newRow[i] = row[i];
            }

            for(int i=0;i<row.length;i++) {
                if(newRow[i] == 'x') {
                    newRow[i] = '.';
                }
            }

            drawnBoard.add(new String(newRow));
        }

        return drawnBoard;
    }

    private void banSquares(char[][] boardContent, int x, int y) {
        int n = boardContent.length;

        for(int i=0;i<n;i++) {
            boardContent[i][y] = 'x';
        }

        for(int j=0;j<n;j++) {
            boardContent[x][j] = 'x';
        }

        diagonalBan(x, y, -1, -1, boardContent);
        diagonalBan(x, y, 1, 1, boardContent);
        diagonalBan(x, y, -1, 1, boardContent);
        diagonalBan(x, y, 1, -1, boardContent);
    }

    private void diagonalBan(int x, int y, int strideX, int strideY, char[][] boardContent) {
        while(isValidPosition(x, y, boardContent.length)) {
            boardContent[x][y] = 'x';

            x += strideX;
            y += strideY;
        }
    }

    private boolean isValidPosition(int x, int y, int n) {
        return (x >= 0 && x < n && y >= 0 && y < n);
    }
}

public class Main {
    public static void main(String[] args) {
        Solution executor = new Solution();
        List<List<String>> allCases = executor.solveNQueens(9);

        for(List<String> board : allCases) {
            for(String row : board) {
                System.out.println(row);
            }

            System.out.println("-------------------------------\n\n");
        }
    }

    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader()
        {
            br = new BufferedReader(
                    new InputStreamReader(System.in));
        }

        String next()
        {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() { return Integer.parseInt(next()); }

        long nextLong() { return Long.parseLong(next()); }

        double nextDouble()
        {
            return Double.parseDouble(next());
        }

        String nextLine()
        {
            String str = "";
            try {
                if(st.hasMoreTokens()){
                    str = st.nextToken("\n");
                }
                else{
                    str = br.readLine();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }
}