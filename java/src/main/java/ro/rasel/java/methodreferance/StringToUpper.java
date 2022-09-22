package ro.rasel.java.methodreferance;

import java.util.Optional;
import java.util.stream.Stream;

public class StringToUpper {
    public static void main(String[] args) {
        Stream.of("eeny", "meeny", null).forEach(StringToUpper::toUpper);
    }

    private static void toUpper(String s) {
        final Optional<String> string = Optional.ofNullable(s);
        System.out.println(string.map(String::toUpperCase).orElse("dummy"));
    }
}
