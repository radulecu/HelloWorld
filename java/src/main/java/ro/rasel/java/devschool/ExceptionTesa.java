package ro.rasel.java.devschool;

class E0 extends Exception {
}

class E1 extends E0 {
}

public class ExceptionTesa {
    public static void main(String[] args) {
        try {
            throw new E1();
        } catch (E0 e) {
            System.out.println("ex0");
        } catch (Exception e) {
            System.out.println("exception");
        }
    }
}
