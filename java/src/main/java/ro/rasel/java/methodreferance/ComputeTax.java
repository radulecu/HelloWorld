package ro.rasel.java.methodreferance;

import java.util.function.UnaryOperator;

public class ComputeTax {

    public static void main(String[] args) {
        ComputeTax ct = new ComputeTax();
        ct.method();
    }

    void method() {
        System.out.format("Total = %.2f", computeTax(10.00, (p) -> p * 0.05));
        System.out.println();

        System.out.format("Total = %.2f", computeTax(10.00, ComputeTax::func1));
        System.out.println();
        System.out.format("Total = %.2f", computeTax(10.00, p -> func1(p)));
        System.out.println();

        System.out.format("Total = %.2f", computeTax(10.00, this::func2));
        System.out.println();
        System.out.format("Total = %.2f", computeTax(10.00, p -> this.func2(p)));
        System.out.println();
        System.out.format("Total = %.2f", computeTax(10.00, p -> new ComputeTax().func2(p)));
        System.out.println();

        System.out.format("Total = %.2f", computeTax(10.00, AnotherClass::func3));
        System.out.println();
        System.out.format("Total = %.2f", computeTax(10.00, p -> AnotherClass.func3(p)));
        System.out.println();

        System.out.format("Total = %.2f", computeTax(10.00, new AnotherClass()::func4));
        System.out.println();
        System.out.format("Total = %.2f", computeTax(10.00, p -> new AnotherClass().func4(p)));
        System.out.println();
    }

    double computeTax(double price, UnaryOperator<Double> op) {
        return op.apply(price) + price;
    }

    public static Double func1(Double p) {
        return p * 0.05;
    }

    public Double func2(Double p) {
        return p * 0.05;
    }

    private static class AnotherClass {
        public static Double func3(Double p) {
            return p * 0.05;
        }

        public Double func4(Double p) {
            return p * 0.05;
        }
    }
}