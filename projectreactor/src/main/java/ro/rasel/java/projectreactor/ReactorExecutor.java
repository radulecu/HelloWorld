package ro.rasel.java.projectreactor;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ReactorExecutor {

    public static void main(String[] args) throws InterruptedException {
        final ExecutorService executor = Executors.newFixedThreadPool(3);
        final Scheduler scheduler = Schedulers.fromExecutor(executor);
        Flux.range(0, 5)
                .parallel()
                .runOn(scheduler)
                .map(i -> "foo" + i)
                .map(ReactorExecutor::identityAndSleep)
                .subscribe(System.out::println);
        Flux.range(0, 5)
                .parallel()
                .runOn(scheduler)
                .map(i -> "boo" + i)
                .map(ReactorExecutor::identityAndSleep)
                .subscribe(System.out::println);
        System.out.println("done");
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.MINUTES);
        System.out.println("shutdown");
    }

    private static <V> V identityAndSleep(V v) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return v;
    }
}
