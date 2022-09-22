package ro.rasel.java.streams;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class StreamTest {
    public static void main(String[] args) {
        testIntStream();
        System.out.println(IntStream.range(0, 100).parallel().mapToObj(value -> "-").collect(Collectors.joining()));
        testSupplier();
        System.out.println(IntStream.range(0, 100).parallel().mapToObj(value -> "-").collect(Collectors.joining()));
        numericStreamsGenerateWithLimitVsRange();
        System.out.println(IntStream.range(0, 100).parallel().mapToObj(value -> "-").collect(Collectors.joining()));
        testCollect();

        LongStream.range(0,Long.MAX_VALUE).peek(System.out::println).parallel().count();
    }

    private static void testIntStream() {
        int[] collect = IntStream.rangeClosed(1, 100).parallel()
                .skip(10)
                .limit(10)
                .filter(StreamTest::isOdd)
                .peek(x -> System.out.println(x + ":" + Thread.currentThread().getId()))
                .toArray();
        System.out.println(Arrays.toString(collect));
    }

    private static boolean isOdd(int value) {
        return value % 2 == 0;
    }

    private static void testSupplier() {
        int limit = 20;
        List<Cuboid> collect = getCuboidsStream(10, limit).parallel()
                .collect(Collectors.toList());
        System.out.println(collect);
    }

    private static void numericStreamsGenerateWithLimitVsRange() {
        Set<Long> threads = LongStream.range(0, 100)
                .limit(100)
                .boxed()
                .map(operand -> Thread.currentThread().getId())
                .collect(Collectors.toSet());
        System.out.println("range threads " + threads);

        Set<Long> paralelThreads = LongStream.range(0, 100)
                .parallel()
                .limit(100)
                .boxed()
                .map(operand -> Thread.currentThread().getId())
                .collect(Collectors.toSet());
        System.out.println("range paralel threads " + paralelThreads);

        System.out.println();
        System.out.println("generate serial version");
        AtomicLong atomicLong = new AtomicLong(0);
        Set<Long> threadsWithLimit = LongStream.generate(atomicLong::getAndIncrement)
                .limit(100)
                .boxed()
                .collect(Collectors.toSet());
        System.out.println("generate with atomicLong " + threadsWithLimit);

        System.out.println();
        System.out.println("paralel with range");
        Set<Long> paralelThreadsWithLimit = LongStream.range(0, 100)
                .parallel()
                .limit(100)
                .boxed()
                .collect(Collectors.toSet());
        System.out.println("paralel threads with range" + paralelThreadsWithLimit);

        System.out.println();
        System.out.println("ISSUE: paralel limit version does not actually uses a single thread:");

        Set<Long> parallelThreadsWithLimit2 = LongStream.generate(() -> Thread.currentThread().getId())
                .parallel()
                .limit(100)
                .boxed()
                .collect(Collectors.toSet());
        System.out.println("limit paralel threads (generate thread id version): " + parallelThreadsWithLimit2);

        Set<Long> parallelThreadsWithLimit3 = LongStream.generate(new Random()::nextInt)
                .parallel()
                .limit(100)
                .boxed()
                .map(operand -> Thread.currentThread().getId())
                .collect(Collectors.toSet());
        System.out.println("limit paralel threads (map thread id version): " + parallelThreadsWithLimit3);

        System.out.println();
        System.out.println(
                "ISSUE: paralel limit version does not guarantee ordering or respect the range " +
                        "and calls supplier more than the limit:");
        atomicLong.set(0);
        Set<Long> parallelThreadsWithLimit = LongStream.generate(atomicLong::getAndIncrement)
                .parallel()
                .limit(100)
                .boxed()
                .collect(Collectors.toSet());
        System.out.println("paralel generate with atomicLong " + parallelThreadsWithLimit);
        System.out.println("suplier calls: " + atomicLong.get());

        atomicLong.set(0);
        //noinspection ResultOfMethodCallIgnored
        LongStream.generate(() -> 0)
                .parallel()
                .limit(100)
                .boxed()
                .map(aLong -> atomicLong.getAndIncrement())
                .collect(Collectors.toSet());
        System.out.println("map calls: " + atomicLong.get());
    }

    @SuppressWarnings("SimplifyStreamApiCallChains")
    private static void testCollect() {
        List<Cuboid> cuboids = getCuboidsStream(3, 1000).parallel()
                .collect(Collectors.toList());
        List<Cuboid> list = cuboids.stream().collect(Collectors.toList());
        Set<Cuboid> set = cuboids.stream().collect(Collectors.toSet());
        LinkedHashSet<Cuboid> collection = cuboids.stream().collect(Collectors.toCollection(LinkedHashSet::new));

        System.out.println(list.size());
        System.out.println(set.size());
        System.out.println(collection.size());

    }

    private static Stream<Cuboid> getCuboidsStream(int bound, int limit) {
        Random random = new Random();
        return Stream.generate(() -> new Cuboid(random.nextInt(10),
                random.nextInt(bound),
                random.nextInt(10),
                Color.values()[random.nextInt(Color.values().length)]))
                .limit(limit);
    }

    private static class Cuboid {

        private final int length;
        private final int width;
        private final int height;
        private final Color color;

        public Cuboid(int length, int width, int height, Color color) {
            this.length = length;
            this.width = width;
            this.height = height;
            this.color = color;
        }

        public int getLength() {
            return length;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public Color getColor() {
            return color;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Cuboid cuboid = (Cuboid) o;
            return length == cuboid.length &&
                    width == cuboid.width &&
                    height == cuboid.height &&
                    color == cuboid.color;
        }

        @Override
        public int hashCode() {
            return Objects.hash(length, width, height, color);
        }

        @Override
        public String toString() {
            return "Cuboid{" +
                    "length=" + length +
                    ", width=" + width +
                    ", height=" + height +
                    ", color=" + color +
                    '}';
        }
    }

    private enum Color {
        RED,
        GREEN,
        BLUE,
        YELLOW,
    }
}
