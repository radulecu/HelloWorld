package ro.rasel.java.streams.string;

import ro.rasel.java.streams.EncodingUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ByteToStringTest {
    public static void main(String[] args) throws UnsupportedEncodingException {
        encoding();
        System.out.println(IntStream.range(0, 128).mapToObj(value -> "*").collect(Collectors.joining()));
        base64Conversion();
        System.out.println(IntStream.range(0, 128).mapToObj(value -> "*").collect(Collectors.joining()));
        stringToBytes();
    }

    private static void base64Conversion() {
        String collect = Stream.iterate('0', i -> ++i).limit(10).map(c -> c + "").collect(Collectors.joining());
        collect+=Stream.iterate('a',i -> ++i).limit('z'-'a'+1).map(c -> c+"").collect(Collectors.joining());
        collect+=Stream.iterate('A', i -> ++i).limit('Z'-'A'+1).map(c -> c+"").collect(Collectors.joining());
        System.out.println(collect);
        byte[] encoded = EncodingUtils.base64ToString(collect).getBytes();
        System.out.println(Arrays.toString(encoded));
    }

    private static void encoding() throws UnsupportedEncodingException {
        String s = IntStream.range(0, 256).mapToObj((int i) -> (char) i)
                .map(Object::toString).collect(Collectors.joining());
        String urlEncodedData = URLEncoder.encode(s, "UTF-8");
        System.out.println(s);
        System.out.println();
        System.out.println(urlEncodedData);
        IntStream.range(0, s.length()).mapToObj(i -> {
            String charAsString = s.charAt(i) + "";
            try {
                return URLEncoder.encode(charAsString, "UTF-8") + " - " + i + " - " + charAsString;
            } catch (UnsupportedEncodingException e) {
                throw new IllegalStateException(e);
            }
        }).forEach(System.out::println);

    }

    private static void stringToBytes() {
        String s = IntStream.rangeClosed(0, 256).mapToObj((int i) -> (char) i)
                .map(Object::toString).collect(Collectors.joining());
        byte[] bytes = s.getBytes();
        byte[] bytes2 = s.getBytes(StandardCharsets.UTF_8);
        byte[] bytes3 = s.getBytes(StandardCharsets.ISO_8859_1);
        System.out.println(Arrays.toString(bytes));
        System.out.println(Arrays.toString(bytes2));
        System.out.println(Arrays.toString(bytes3));
        System.out.println(new String(bytes));
        System.out.println(new String(bytes2, StandardCharsets.UTF_8));
        System.out.println(new String(bytes2, StandardCharsets.ISO_8859_1));
    }
}
