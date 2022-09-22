package ro.rasel.java.projectreactor;

import reactor.core.publisher.Mono;

import java.time.Duration;

public class MonoTest {

    public static final MonoTest MONO_TEST = new MonoTest();

    public static void main(String[] args) throws Exception {
        MONO_TEST.testMono1();
        MONO_TEST.testMono2();
    }

    private void testMono1() throws InterruptedException {
        Mono.never().doAfterTerminate(() -> System.out.println("done none")).subscribe();
        Mono.empty().doAfterTerminate(() -> System.out.println("done empty")).subscribe();
        Thread.sleep(100);
    }

    private void testMono2() throws InterruptedException {
        final Mono<String> second = Mono.delay(Duration.ofMillis(10)).map(i -> "goo" + i);
        Mono.delay(Duration.ofMillis(10))
                .map(i -> "foo" + i)
                .or(second)
                .subscribe(System.out::println);
        Thread.sleep(100);
    }
}
