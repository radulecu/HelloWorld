package ro.rasel.java.polimorfism;

public class PolimorfismTest {

    public static void main(String[] args) {
        Third third = new Third();
        Second second = third;
        First first = second;
        System.out.println(first.string + " " + first.stringStatic + " " + First.stringStatic);
        System.out.println(second.string + " " + second.stringStatic + " " + Second.stringStatic);
        System.out.println(third.string + " " + third.stringStatic + " " + Third.stringStatic);

        System.out.println();

        System.out.println(first.getString() + " " + first.getStringStatic() + " " + First.getStringStatic());
        System.out.println(second.getString() + " " + second.getStringStatic() + " " + Second.getStringStatic());
        System.out.println(third.getString() + " " + third.getStringStatic() + " " + Third.stringStatic);

    }
}

class First {
    String string = "In First";
    static String stringStatic = "In First static";

    public String getString() {
        return string;
    }

    public static String getStringStatic() {
        return stringStatic;
    }
}

class Second extends First {
    String string = "in Second";
    static String stringStatic = "In Second static";

    public String getString() {
        return string;
    }

    public static String getStringStatic() {
        return stringStatic;
    }
}

class Third extends Second {
    String string = "in Third";
    static String stringStatic = "In Third static";

    public String getString() {
        return string;
    }

    public static String getStringStatic() {
        return stringStatic;
    }
}
