//package ro.rasel.java.interview;
//
//import java.util.HashSet;
//import java.util.Random;
//import java.util.Set;
//
//public class RandomNumber {
//    private final int n;
//    private final Set<Integer> generatedNumbers = new HashSet<>();
//
//    public RandomNumber(int n) {
//        this.n = n;
//    }
//
//    public int randomNumber() {
//        int next;
//
//        if (generatedNumbers.size() == n) {
//            generatedNumbers.clear();
//        }
//
//        do {
//            next = new Random().nextInt(n);
//        } while (generatedNumbers.contains(next));
//
//        generatedNumbers.add(next);
//
//        return next;
//    }
//
//
//}