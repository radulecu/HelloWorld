package ro.rasel.java.devschool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Piles {
    public static void main(String[] args) {
        final List<Integer> boxesInPiles = Arrays.asList(4, 5, 5, 4, 2);
        long sum = getSum(boxesInPiles);
        System.out.println(sum);
    }

    private static long getSum(List<Integer> boxesInPiles) {
        final TreeMap<Integer, Long> collect = boxesInPiles.stream().collect(Collectors.groupingBy(
                Function.identity(), TreeMap::new, Collectors.counting()));
        final List<Long> integers =
                new ArrayList<>(collect.values());
        return IntStream.range(0, integers.size()).mapToLong(i -> i * integers.get(i)).sum();
    }
}
