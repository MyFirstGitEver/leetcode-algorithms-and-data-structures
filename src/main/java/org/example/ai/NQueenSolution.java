package org.example.ai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

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

public class NQueenSolution {
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
