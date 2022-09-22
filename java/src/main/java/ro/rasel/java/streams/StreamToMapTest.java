package ro.rasel.java.streams;

import ro.rasel.java.utils.Touple;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamToMapTest {
    public static void main(String[] args) {
        convertToMap();
        convertToMapWithNullValue();
    }

    private static void convertToMap() {
        final List<Touple<Integer, String>> touples = Arrays.asList(new Touple<>(1, "1"), new Touple<>(2, "2"));
        System.out.println(asMap(touples));
    }

    private static void convertToMapWithNullValue() {
        final List<Touple<Integer, String>> touples = Arrays.asList(new Touple<>(1, "1"), new Touple<>(2, null));
        System.out.println(asMap(touples));
    }

    private static Map<Integer, String> asMap(List<Touple<Integer, String>> touples) {
        return touples.stream()
                .filter(integerStringTouple -> integerStringTouple.getQ()!=null)
                .collect(Collectors
                .toMap(Touple::getP,
                        Touple::getQ));
    }
}
