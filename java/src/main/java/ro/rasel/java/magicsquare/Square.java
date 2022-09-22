package ro.rasel.java.magicsquare;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Square {
    private int[][] matrix;

    Square(int[][] matrix) {
        this.matrix = matrix;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    Square copy() {
        final int[][] matrix = getMatrix();
        int[][] copy = new int[matrix.length][];
        for (int i = 0; i < copy.length; i++) {
            copy[i] = Arrays.copyOf(matrix[i], matrix[i].length);
        }

        return new Square(copy);
    }

    private int computeDelta(Square otherSquare) {
        int sum = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sum += Math.abs(matrix[i][j] - otherSquare.getMatrix()[i][j]);
            }
        }
        return sum;
    }

    @Override
    public String toString() {
        return Arrays.stream(matrix)
                .map(Arrays::toString)
                .collect(Collectors.joining());
    }
}
