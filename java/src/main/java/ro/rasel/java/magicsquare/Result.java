package ro.rasel.java.magicsquare;

public class Result {
    private int delta;
    private Square square;

    public Result(Square square, int delta) {
        this.delta = delta;
        this.square = square.copy();
    }

    public int getDelta() {
        return delta;
    }

    public void setDelta(int delta) {
        this.delta = delta;
    }

    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
    }

    @Override
    public String toString() {
        return "Result{" +
                "delta=" + delta +
                ", square=" + square +
                '}';
    }
}
