package ro.rasel.java.collections;

public class ForEach {
    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            Thread t = new Thread(() -> {
                for (int j = 0; j > -1; ) {
                    System.out.println("testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest");
                }
            });
            t.start();
        }
    }
}
