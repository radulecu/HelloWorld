package ro.rasel.java.projectreactor;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FluxTest {

    public static final FluxTest FLUX_TEST = new FluxTest();

    public static void main(String[] args) throws Exception {
        FLUX_TEST.testFlux1();
        FLUX_TEST.testFlux2();
        FLUX_TEST.testFlux3();
    }

    private void testFlux2() throws InterruptedException {
        Flux.fromIterable(IntStream.range(0, 1000).boxed().collect(Collectors.toList()))
                .delayElements(Duration.ofMillis(100))
                .doOnNext(this::someObserver)
                .map(d -> d * 2)
                .map(this::identityRandomThrowghError)
                .take(10)
                .onErrorContinue(this::fallback)
//                .doAfterTerminate(this::incrementTerminate)
                .subscribe(System.out::println);
        Thread.sleep(2000);
    }

    private void testFlux3() throws InterruptedException {
        Flux.interval(Duration.ofMillis(100)).take(10).subscribe(System.out::println);
        Thread.sleep(2000);
    }

    private void testFlux1() throws InterruptedException {
        Flux.just("A", "B").map(this::peek).subscribe();
        try {
            Flux.error(new RuntimeException()).subscribe();
        } catch (RuntimeException e) {
            System.out.println("exception thrown");
        }
        Thread.sleep(20);
    }

    private <V> V peek(V v) {
        System.out.println(v);
        return v;
    }

    private <T> T identityRandomThrowghError(T t) {
        if (new Random().nextInt(3) == 0) {
            throw new RuntimeException();
        }
        return t;
    }

    private void someObserver(Integer integer) {
        System.out.println("someObserver(" + integer + ")");
    }

    private void fallback(Throwable throwable, Object o) {
        throwable.printStackTrace();
    }

    private void incrementTerminate() {
        System.out.println("incrementTerminate())");
    }
}
