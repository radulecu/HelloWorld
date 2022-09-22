package ro.rasel.java.projectreactor;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ReactorPublishVsSubscribeTest {

    public static void main(String[] args) throws InterruptedException {
        final ExecutorService executor = Executors.newFixedThreadPool(1);
        final Scheduler scheduler = Schedulers.fromExecutor(executor);
        Flux.range(0, 5)
                .map(i -> "foo" + i)
                .map(ReactorPublishVsSubscribeTest::identityAndPrint)
                .subscribeOn(scheduler)
                .map(ReactorPublishVsSubscribeTest::identityAndSleep)
                .subscribe(System.out::println);
        Flux.range(0, 5)
                .map(i -> "boo" + i)
                .map(ReactorPublishVsSubscribeTest::identityAndPrint)
                .publishOn(scheduler)
                .map(ReactorPublishVsSubscribeTest::identityAndSleep)
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

    private static <V> V identityAndPrint(V v) {
        System.out.println("***"+v);
        return v;
    }
}
