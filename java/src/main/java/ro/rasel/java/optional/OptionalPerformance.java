package ro.rasel.java.optional;

import java.util.Optional;

public class OptionalPerformance {
    private final long max;

    public OptionalPerformance(long max) {
        this.max = max;
    }

    public void iterateWithOptional() {
        Optional<Long> start = Optional.of(0L);
        for (long i = 0; i < max; i++) {
            start = start.map(v -> v + 1);
        }
    }

    public void iterateWithLongWrapper() {
        Long start = 0L;
        for (long i = 0; i < max; i++) {
            start++;
        }
    }

    public void iterateWithLong() {
        long start = 0L;
        for (long i = 0; i < max; i++) {
            start++;
        }
    }
}
