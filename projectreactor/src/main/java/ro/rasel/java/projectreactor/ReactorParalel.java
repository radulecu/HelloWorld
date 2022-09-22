package ro.rasel.java.projectreactor;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

public class ReactorParalel {

    public static void main(String[] args) throws InterruptedException {
        final Scheduler parallel = Schedulers.elastic();
        Flux.range(0, 200)
                .parallel(100)
                .runOn(parallel)
                .map(i -> "foo" + i)
                .map(ReactorParalel::identityAndSleep)
                .subscribe(System.out::println);
        Flux.range(0, 200)
                .parallel(100)
                .runOn(parallel)
                .map(i -> "boo" + i)
                .map(ReactorParalel::identityAndSleep)
                .doOnError(ReactorParalel::error)
                .subscribe(System.out::println);
        System.out.println("done");
        Thread.sleep(10000);
    }

    private static void error(Throwable throwable) {
        throwable.printStackTrace();
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
