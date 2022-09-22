package ro.rasel.java.methodreferance;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class Readings {

    public static void main(String[] args) {
        List<Reading> readings = createReadingList();

        readings.sort(Readings::compareReadings);
        readings.forEach(computeFunction(System.out::println));
    }

    private static Consumer<Reading> computeFunction(Consumer<Double> consumer) {
        return r -> consumer.accept(r.value);
    }

    private static int compareReadings(Reading r1, Reading r2) {
        return r1.value < r2.value ? -1 : 1;
    }

    private static List<Reading> createReadingList() {
        return Arrays.asList(
                new Reading(2017, 1, 1, 405.91),
                new Reading(2017, 1, 8, 405.98),
                new Reading(2017, 1, 15, 406.14),
                new Reading(2017, 1, 22, 406.48),
                new Reading(2017, 1, 29, 406.20),
                new Reading(2017, 2, 5, 406.03));
    }

}

class Reading {
    int year;

    public Reading(int year, int month, int day, double value) {
        super();
        this.year = year;
        this.month = month;
        this.day = day;
        this.value = value;
    }

    int month;
    int day;
    double value;
}