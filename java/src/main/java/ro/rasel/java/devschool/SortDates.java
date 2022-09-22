package ro.rasel.java.devschool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SortDates {

    public static void main(String[] args) {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
        final List<String> dates = Arrays.asList(
                "02 Mar 2023",
                "03 Jan 2023",
                "03 Jand 2021"
        );
        final List<String> collect = dates.stream().map(s -> {
            try {
                return simpleDateFormat.parse(s);
            } catch (ParseException e) {
                throw new IllegalArgumentException("cannot parse string: " + s, e);
            }
        }).sorted().map(simpleDateFormat::format).collect(Collectors.toList());
        System.out.println(collect);
    }
}
