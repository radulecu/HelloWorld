package ro.rasel.java.optional;

import org.junit.Test;

public class OptionalPerformanceTest {
    public static final OptionalPerformance OPTIONAL_PERFORMANCE = new OptionalPerformance(10_000_000_000L);

    @Test
    public void iterateWithOptional() {
        OPTIONAL_PERFORMANCE.iterateWithOptional();
    }

    @Test
    public void iterateWithLongWrapper() {
        OPTIONAL_PERFORMANCE.iterateWithLongWrapper();
    }

    @Test
    public void iterateWithLong() {
        OPTIONAL_PERFORMANCE.iterateWithLong();
    }
}