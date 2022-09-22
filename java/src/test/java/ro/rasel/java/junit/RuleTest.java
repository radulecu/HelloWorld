package ro.rasel.java.junit;

import org.junit.Rule;
import org.junit.Test;

public class RuleTest {
    @Rule
    public MyRule myRule = new MyRule();

    @Test
    public void test1() {
        System.out.println("in test1: " + myRule);
    }

    @Test
    public void test2() {
        System.out.println("in test2: " + myRule);
    }

    @Test
    public void test3() {
        System.out.println("in test3: " + myRule);
    }
}
