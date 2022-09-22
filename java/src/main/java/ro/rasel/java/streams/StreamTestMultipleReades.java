package ro.rasel.java.streams;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StreamTestMultipleReades {
    public static void main(String[] args) {
        final int first = IntStream.iterate(2, n -> n + 1).findFirst().getAsInt();
        final List<Integer> list =
                IntStream.iterate(2, n -> n + 1).skip(1).filter((int value) -> value % first != 0).limit(5).boxed()
                        .collect(Collectors.toList());
        System.out.println(list);
    }
}
