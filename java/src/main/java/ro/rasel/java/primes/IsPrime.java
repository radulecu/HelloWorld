package ro.rasel.java.primes;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class IsPrime {
    private final BitSet cache = new BitSet();
    private int maxComputed = 1;

    public static void main(String[] args) {

        final IsPrime isPrime1 = new IsPrime();

        long start = System.nanoTime();
        long start2 = System.nanoTime();
        final Collection<Integer> allPrimes11 = isPrime1.getAllPrimes(100000);
        System.out.println(Duration.of(System.nanoTime() - start, ChronoUnit.NANOS));
        start = System.nanoTime();
        final Collection<Integer> allPrimes12 = isPrime1.getAllPrimes(400000);
        System.out.println(Duration.of(System.nanoTime() - start, ChronoUnit.NANOS));
        start = System.nanoTime();
        final Collection<Integer> allPrimes13 = isPrime1.getAllPrimes(300000);
        System.out.println(Duration.of(System.nanoTime() - start, ChronoUnit.NANOS));
        start = System.nanoTime();
        final Collection<Integer> allPrimes14 = isPrime1.getAllPrimes(400000);
        System.out.println(Duration.of(System.nanoTime() - start, ChronoUnit.NANOS));
        System.out.println("total " +Duration.of( System.nanoTime() - start2, ChronoUnit.NANOS));

        start = System.nanoTime();
        final List<Integer> allPrimes21 =
                IntStream.rangeClosed(1, 400000).filter(new IsPrime()::isPrime).boxed()
                        .collect(Collectors.toList());
        System.out.println(Duration.of(System.nanoTime() - start, ChronoUnit.NANOS));

        start = System.nanoTime();
        final List<Integer> allPrimes22 =
                IntStream.rangeClosed(1, 400000).filter(new IsPrime()::isPrime).boxed().parallel()
                        .collect(Collectors.toList());
        System.out.println(Duration.of(System.nanoTime() - start, ChronoUnit.NANOS));
    }

    public Collection<Integer> getAllPrimes(int maxValue) {
        if (maxValue <= 0) {
            throw new IllegalArgumentException("must be pozitive");
        } else if (maxValue == 1) {
            return new HashSet<>();
        }

        computeAllPrimes(maxValue, maxComputed, cache);

        if (maxComputed < maxValue) {
            maxComputed = maxValue;
        }

        return IntStream.rangeClosed(2, maxValue).filter(cache::get).boxed()
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private static void computeAllPrimes(int maxValue, int maxComputed, BitSet cache) {
        for (int i = Math.min(maxValue, maxComputed) + 1; i < maxValue; i++) {
            cache.set(i, isPrime(i, cache));
        }
    }

    private static boolean isPrime(int number, BitSet cache) {
        return !IntStream.rangeClosed(2, number / 2)
                .filter(value -> number % value == 0).findFirst().isPresent();
    }

    public boolean isPrime(int number) {
        return !IntStream.rangeClosed(2, number / 2)
                .filter(value -> number % value == 0).findFirst().isPresent();
    }

}
