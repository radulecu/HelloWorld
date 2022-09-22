package ro.rasel.java.magicsquare;

import java.time.Duration;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

public class MagicSquare {

    public static final int MAX_VALUE = 10;

    public static void main(String[] args) {
        test(3, 3, 5);
        test(4, 4, 11);
        test(5, 5, 7);
        test(6, 6, 7);
        test(7, 7, 7);
        test(8, 8, 7);
        test(9, 9, 7);
        test(10, 10, 7);
        test(11, 11, 7);
        test(12, 12, 7);
    }

    private static void test(int sum, int matrixSize, int seed) {
        Random r = new Random(seed);
        int[][] matrix = new int[matrixSize][matrixSize];
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                matrix[i][j] = r.nextInt(MAX_VALUE);
            }
        }

        System.out.println(sum);
        System.out.println(new Square(matrix));

        System.out.println(getClosestMagicSquare(new Square(matrix), sum));
        System.out.println(getClosestMagicSquare2(new Square(matrix), sum));
        System.out.println();
    }

    public static Result getClosestMagicSquare1(Square square, int s) {
        final long nanoTime = System.nanoTime();
        try {
            return getClosestMagicSquare1(square.getMatrix(), square.copy().getMatrix(), s, 0, 0);
        } finally {
            System.out.println(Duration.ofNanos(System.nanoTime() - nanoTime));
        }
    }

    public static Result getClosestMagicSquare2(Square square, int s) {
        final long nanoTime = System.nanoTime();
        try {
            return getClosestMagicSquare2(square.getMatrix(), square.copy().getMatrix(), s, 0, 0);
        } finally {
            System.out.println(Duration.ofNanos(System.nanoTime() - nanoTime));
        }
    }

    public static Result getClosestMagicSquare(Square square, int s) {
        final long nanoTime = System.nanoTime();
        try {
            return getClosestMagicSquare(square.getMatrix(), square.copy().getMatrix(), s, 0, 0, Integer.MAX_VALUE);
        } finally {
            System.out.println(Duration.ofNanos(System.nanoTime() - nanoTime));
        }
    }

    public static Result getClosestMagicSquare1(int[][] originalMatrix, int[][] matrix, int s, int pos, int delta) {
        final int matrixSize = originalMatrix.length;
        if (pos >= matrixSize * matrixSize) {
            return isMagic(matrix, s) ? new Result(new Square(matrix), delta) : null;
        }
        Result best = null;

        int originalVal = originalMatrix[pos / matrixSize][pos % matrixSize];
        for (int i = 0; i < MAX_VALUE; i++) {
            matrix[pos / matrixSize][pos % matrixSize] = i;
            best = getBestResult(
                    getClosestMagicSquare1(originalMatrix, matrix, s, pos + 1, delta + Math.abs(originalVal - i)
                    ), best);
        }

        return best;
    }

    public static Result getClosestMagicSquare2(int[][] originalMatrix, int[][] matrix, int s, int pos, int delta) {
        final int matrixSize = originalMatrix.length;
        if (pos == matrixSize * matrixSize) {
            return isMagic(matrix, s) ? new Result(new Square(matrix), delta) : null;
        }
        Result best = null;

        int originalVal = originalMatrix[pos / matrixSize][pos % matrixSize];
        int crtRow = pos / matrixSize;
        int crtCol = pos % matrixSize;
        if (crtCol == matrixSize - 1) {
            int val = s - IntStream.range(0, crtCol).map(i -> matrix[crtRow][i]).sum();
            if (val >= 0 && val < 10) {
                matrix[pos / matrixSize][pos % matrixSize] = val;
                best = getBestResult(
                        getClosestMagicSquare2(originalMatrix, matrix, s, pos + 1,
                                delta + Math.abs(originalVal - val)
                        ), best);
            }
        } else if (crtRow == matrixSize - 1) {
            int val = s - IntStream.range(0, crtRow).map(i -> matrix[i][crtCol]).sum();
            if (val >= 0 && val < 10) {
                matrix[pos / matrixSize][pos % matrixSize] = val;
                best = getBestResult(
                        getClosestMagicSquare2(originalMatrix, matrix, s, pos + 1,
                                delta + Math.abs(originalVal - val)
                        ), best);
            }
        } else {
            for (int i = 1; i < MAX_VALUE; i++) {
                matrix[pos / matrixSize][pos % matrixSize] = i;
                best = getBestResult(
                        getClosestMagicSquare2(originalMatrix, matrix, s, pos + 1, delta + Math.abs(originalVal - i)
                        ), best);
            }
        }

        return best;
    }

    public static Result getClosestMagicSquare(int[][] originalMatrix, int[][] matrix, int s, int pos, int delta,
            int minDelta) {
        final int matrixSize = originalMatrix.length;
        if (pos == matrixSize * matrixSize) {
            return isMagic(matrix, s) ? new Result(new Square(matrix), delta) : null;
        }
        if (delta > minDelta) {
            return null;
        }

        Result best = null;

        int originalVal = originalMatrix[pos / matrixSize][pos % matrixSize];
        int crtRow = pos / matrixSize;
        int crtCol = pos % matrixSize;
        if (crtCol == matrixSize - 1) {
            int val = s - IntStream.range(0, crtCol).map(i -> matrix[crtRow][i]).sum();
            if (val >= 0 && val < 10) {
                matrix[pos / matrixSize][pos % matrixSize] = val;
                best = getBestResult(
                        getClosestMagicSquare(originalMatrix, matrix, s, pos + 1,
                                delta + Math.abs(originalVal - val),minDelta
                        ), best);
            }
        } else if (crtRow == matrixSize-1) {
            int val = s - IntStream.range(0, crtRow).map(i -> matrix[i][crtCol]).sum();
            if (val >= 0 && val < 10) {
                matrix[pos / matrixSize][pos % matrixSize] = val;
                best = getBestResult(
                        getClosestMagicSquare(originalMatrix, matrix, s, pos + 1,
                                delta + Math.abs(originalVal - val), minDelta
                        ), best);
            }
        } else {
            for (int i = 1; i < MAX_VALUE; i++) {
                matrix[pos / matrixSize][pos % matrixSize] = i;
                best = getBestResult(
                        getClosestMagicSquare(originalMatrix, matrix, s, pos + 1, delta + Math.abs(originalVal - i),
                                minDelta), best);
                minDelta = Math.min(minDelta, best != null ? best.getDelta() : Integer.MAX_VALUE);
            }
        }

        return best;
    }

//    public static Result getClosestMagicSquare(Square square, int s) {
//        Result best = null;
//        for (int i = 3 * 3 - 1; i >= 0; i--) {
//            best = getBestResult(getClosestMagicSquare(square, s, i, 0), best);
//        }
//        return best;
//    }
//
//    public static Result getClosestMagicSquare(Square square, int s, int pos, int delta) {
//        if (pos >= Math.pow(3, 2)) {
//            return square.isMagic(s) ? new Result(square, delta) : null;
//        }
//        Result best = getClosestMagicSquare(square, s, pos + 1, delta);
//        int originalVal = square.getMatrix()[pos / 3][pos % 3];
//        for (int j = 0; j < 10; j++) {
//            int val = originalVal + j;
//            if (val >= 0 && val < 3) {
//                square.getMatrix()[pos / 3][pos % 3] = val;
//                Result r = getClosestMagicSquare(square, s, pos + 1, delta + j);
//                best = getBestResult(best, r);
//            }
//            val = originalVal - j;
//            if (val >= 0 && val < 3) {
//                square.getMatrix()[pos / 3][pos % 3] = val;
//                Result r = getClosestMagicSquare(square, s, pos + 1, delta + j);
//                best = getBestResult(best, r);
//            }
//        }
//        return best;
//    }

    private static Result getBestResult(Result r1, Result r2) {
        return r1 == null ?
                r2 :
                r2 == null ?
                        r1 :
                        r1.getDelta() < r2.getDelta() ?
                                r1 :
                                r2;
    }

    public static boolean isMagic(int[][] matrix, int s) {
        return Optional.of(matrix)
                .filter(m -> IntStream.range(0, m.length).map(i -> m[i][i]).sum() == s)
                .filter(m ->
                        IntStream.range(0, m.length).map(i -> m[i][m.length - 1 - i]).sum() == s)
                .filter(m -> IntStream.range(0, m.length)
                        .allMatch(i -> IntStream.range(0, m[i].length).map(j -> m[i][j]).sum() == s))
                .filter(m -> IntStream.range(0, m[0].length)
                        .allMatch(i -> IntStream.range(0, m.length).map(j -> m[j][i]).sum() == s))
                .isPresent();
    }
}
