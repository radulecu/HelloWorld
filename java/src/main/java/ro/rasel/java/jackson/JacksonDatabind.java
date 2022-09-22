package ro.rasel.java.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.beans.ConstructorProperties;
import java.io.IOException;
import java.util.StringJoiner;

public class JacksonDatabind {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        final C c = new C("s2");
        final B b = new B("s1", c);
        A a = new A(123, b);

        final String s = OBJECT_MAPPER.writeValueAsString(a);
        System.out.println(s);
        System.out.println(OBJECT_MAPPER.readValue(s, A.class));
    }

    static class A {
        private final int i;
        private final B b;

        @ConstructorProperties({"i", "b"})
        A(int i, B b) {
            this.i = i;
            this.b = b;
        }

        public int getI() {
            return i;
        }

        public B getB() {
            return b;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", A.class.getSimpleName() + "[", "]")
                    .add("i=" + i)
                    .add("b=" + b)
                    .toString();
        }
    }

    static class B {
        private final String s;
        private final C c;

        @ConstructorProperties({"s", "b"})
        B(String s, C c) {
            this.s = s;
            this.c = c;
        }

        public String getS() {
            return s;
        }

        public C getC() {
            return c;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", B.class.getSimpleName() + "[", "]")
                    .add("s='" + s + "'")
                    .add("c=" + c)
                    .toString();
        }
    }

    static class C {
        private final String s;

        @ConstructorProperties({"s"})
        C(String s) {
            this.s = s;
        }

        public String getS() {
            return s;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", C.class.getSimpleName() + "[", "]")
                    .add("s='" + s + "'")
                    .toString();
        }
    }
}
