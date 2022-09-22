package ro.rasel.java.streams;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ExceptionTest {
    public static void main(String[] args) throws InterruptedException {
        try {
            serialExceptionTest();
        } catch (Exception e) {
            Thread.sleep(1000);
            e.printStackTrace();
        }

        try {
            paralelExceptionTest();
        } catch (Exception e) {
            Thread.sleep(1000);
            e.printStackTrace();
        }

        try {
            CompletableFuture.supplyAsync(() -> {
                throw new RuntimeException("test");
            }).join();
        } catch (Exception e) {
            Thread.sleep(1000);
            e.printStackTrace();
        }
    }

    private static List<Integer> serialExceptionTest() {
        System.out.println("before serial" + Thread.currentThread());
        return IntStream.range(0, 1_000_000).map(integer -> {
            if (integer % 10 == 0) {
                System.out.println(Thread.currentThread());
                throw new NullPointerException("test");
            }
            return integer * 2;
        }).boxed().collect(Collectors.toList());
    }

    private static List<Integer> paralelExceptionTest() {
        System.out.println("before paralel" + Thread.currentThread());
        return IntStream.range(0, 1_000_000).parallel().map(integer -> {
            if (integer % 10 == 0 && !Thread.currentThread().getName().contains("main")) {
                System.out.println(Thread.currentThread());
                throw new NullPointerException("test in thread " + Thread.currentThread());
            }
            return integer * 2;
        }).boxed().collect(Collectors.toList());
    }
}
