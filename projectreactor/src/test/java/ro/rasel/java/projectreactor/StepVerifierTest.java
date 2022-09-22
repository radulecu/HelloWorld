package ro.rasel.java.projectreactor;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxProcessor;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

public class StepVerifierTest {

    @Test
    public void test() {
        Flux<String> source = Flux.just("foo", "bar").concatWith(Mono.error(new IllegalArgumentException("error")));
        StepVerifier.create(source).expectNext("foo", "bar").expectError(RuntimeException.class).verify();
        StepVerifier.create(source).expectNext("foo").expectNext("bar").expectErrorMessage("error").verify();
        StepVerifier.create(source).assertNext(s -> s.equals("foo")).expectNext("bar").expectErrorMessage("error").verify();
    }
}