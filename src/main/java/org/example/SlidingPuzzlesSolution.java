package org.example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;


class BoardContainer {
    int[][] board;
    int code;
    int level;

    public BoardContainer(int[][] board, int level) {
        this.board = board;
        this.level = level;
        code = Arrays.deepHashCode(board);
    }

    @Override
    public boolean equals(Object obj) {
        BoardContainer container = (BoardContainer) obj;

        for(int i=0;i<2;i++) {
            for(int j=0;j<3;j++) {
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

class SlidingPuzzlesSolution {
    HashMap<BoardContainer, Boolean> visited = new HashMap<>();

    public int slidingPuzzle(int[][] board) {
        Queue<BoardContainer> queue = new LinkedList<>();

        BoardContainer firstBoard = new BoardContainer(board, 0);
        queue.add(firstBoard);
        visited.put(firstBoard, true);

        int level = -1;
        while(queue.size() != 0) {
            BoardContainer current = queue.remove();
            if(current == null) {
                continue;
            }

            if(found(current.board)) {
                level = current.level;
                break;
            }

            int zeroXPos = 0, zeroYPos = 0;

            for(int i=0;i<2;i++) {
                for(int j=0;j<3;j++) {
                    if(current.board[i][j] == 0) {
                        zeroXPos = i;
                        zeroYPos = j;
                        break;
                    }
                }
            }

            queue.add(buildBoardAt(current.level, zeroXPos, zeroYPos, zeroXPos + 1, zeroYPos, current.board));
            queue.add(buildBoardAt(current.level, zeroXPos, zeroYPos, zeroXPos - 1, zeroYPos, current.board));
            queue.add(buildBoardAt(current.level, zeroXPos, zeroYPos, zeroXPos, zeroYPos - 1, current.board));
            queue.add(buildBoardAt(current.level, zeroXPos, zeroYPos, zeroXPos, zeroYPos + 1, current.board));
        }

        return level;
    }

    private boolean found(int[][] board) {
        return (board[0][0] == 1 && board[0][1] == 2 && board[0][2] == 3 &&
                board[1][0] == 4 && board[1][1] == 5 && board[1][2] == 0);
    }
    private BoardContainer buildBoardAt(int level, int zeroXPos, int zeroYPos, int x, int y, int[][] board) {
        if(!validPosition(x, y)) {
            return null;
        }

        int[][] newBoard = new int[2][3];

        for(int i=0;i<2;i++) {
            for(int j=0;j<3;j++) {
                newBoard[i][j] = board[i][j];
            }
        }

        int temp = newBoard[zeroXPos][zeroYPos];
        newBoard[zeroXPos][zeroYPos] = newBoard[x][y];
        newBoard[x][y] = temp;

        BoardContainer container =  new BoardContainer(newBoard, level + 1);;

        if(visited.get(container) != null) {
            return null; // if visited we should not build this container
        }

        visited.put(container, true);
        return container;
    }

    private boolean validPosition(int x, int y) {
        return (x >= 0 && x < 2) && (y >= 0 && y < 3);
    }
}
