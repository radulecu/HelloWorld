package ro.rasel.java.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CompletableFutureExceptionTest {
    public static void main(String[] args) {
        supplyAsincWithJoin();
        System.out.println(IntStream.range(0, 100).parallel().mapToObj(value -> "-").collect(Collectors.joining()));
        supplyAsincWithFutureTask();
        System.out.println(IntStream.range(0, 100).parallel().mapToObj(value -> "-").collect(Collectors.joining()));
        supplyAsincWithFutureTaskAndJoin();
    }

    private static void supplyAsincWithJoin() {
        CompletableFuture<String> completableFuture =
                CompletableFuture.supplyAsync(() -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    throw new RuntimeException("Just a runtime exception");
                });
        try {
            String s = completableFuture.join();
            System.out.println(s);
        } catch (CompletionException e){
            e.printStackTrace();
        }
    }

    private static Future<String> runAndGet(FutureTask<String> stringFutureTask) {
        stringFutureTask.run();
        return stringFutureTask;
    }

    private static void supplyAsincWithFutureTaskAndJoin() {
        CompletableFuture<Future<String>> completableFuture =
                CompletableFuture.supplyAsync(() -> {
                    FutureTask<String> stringFutureTask = new FutureTask<>(() -> {
                        Thread.sleep(1000);
                        throw new Exception("Just an exception");
                    });
                    return runAndGet(stringFutureTask);
                });
        try {
            CompletableFuture.supplyAsync(() -> "sleepTime").thenAccept(System.out::println)
                    .runAfterBoth(completableFuture, () -> System.out
                            .println("done"));
            Future<String> stringFutureTask = completableFuture.join();
            String s = stringFutureTask.get();
            System.out.println(s);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static void supplyAsincWithFutureTask() {
        CompletableFuture<Future<String>> completableFuture =
                CompletableFuture.supplyAsync(() -> {
                    return runAndGet(new FutureTask<>(() -> {
                        Thread.sleep(1000);
                        throw new Exception("Just an exception");
                    }));
                });
        try {
            Future<String> stringFutureTask = completableFuture.get();
            String s = stringFutureTask.get();
            System.out.println(s);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
