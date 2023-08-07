package org.example;

class RotateImageInPlaceSolution {
    public void rotate(int[][] matrix) {
        if(matrix.length == 1) {
            return;
        }

        int matSize = matrix.length;
        for(int i=0;i<matrix.length-1;i++) {
            rotateBorder(matrix, i, matSize - 1);
            matSize -= 2;
        }
    }

    private void rotateBorder(int[][] matrix, int borderId, int times) {
        int startBound = borderId, endBound = matrix.length - borderId - 1;
        int lastValue = matrix[startBound + 1][startBound];

        for(int i=0;i<times;i++) {
            lastValue = rowRun(lastValue, startBound, endBound, 1, startBound, matrix);

            lastValue = columnRun(lastValue, startBound + 1, endBound, 1, endBound, matrix);

            lastValue = rowRun(lastValue, endBound - 1, startBound, -1, endBound, matrix);

            columnRun(lastValue, endBound - 1, startBound, -1, startBound, matrix);
        }
    }

    public static void print(int[][] matrix) {
        for(int i=0;i<matrix.length;i++) {
            for(int j=0;j<matrix.length;j++) {
                System.out.print(matrix[i][j] + " ");
            }

            System.out.println();
        }
    }

    private int columnRun(int lastValue, int xFrom, int xTo, int step, int y, int[][] matrix) {
        int remember = lastValue;

        for(int i=xFrom;step * i <= xTo * step;i+=step) {
            int value = matrix[i][y];
            matrix[i][y] = remember;
            remember = value;
        }

        return remember;
    }

    private int rowRun(int lastValue, int yFrom, int yTo, int step, int x, int[][] matrix) {
        int remember = lastValue;

        for(int i=yFrom;step * i<= yTo * step;i+=step) {
            int value = matrix[x][i];
            matrix[x][i] = remember;
            remember = value;
        }

        return remember;
    }

    private void driver() {
        int n;

        Main.FastReader reader = new Main.FastReader();
        n = reader.nextInt();

        int[][] mat = new int[n][n];

        for(int i=0;i<n;i++) {
            String[] line = reader.nextLine().split(" ");

            for(int j=0;j<n;j++) {
                mat[i][j] = Integer.parseInt(line[j]);
            }
        }
        RotateImageInPlaceSolution.print(mat);

        RotateImageInPlaceSolution rotateImageInPlaceSolution = new RotateImageInPlaceSolution();
        rotateImageInPlaceSolution.rotate(mat);

        System.out.println();
        RotateImageInPlaceSolution.print(mat);
    }
}