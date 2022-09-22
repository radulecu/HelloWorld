package ro.rasel.java.junit;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import java.util.concurrent.atomic.AtomicInteger;

public class MyRule implements MethodRule {
    private static final AtomicInteger atomicInteger = new AtomicInteger(0);
    private final int i;

    public MyRule() {
        this.i = atomicInteger.incrementAndGet();
    }

    @Override
    public String toString() {
        return "MyRule{" +
                "i=" + i +
                '}';
    }

    @Override
    public Statement apply(Statement statement, FrameworkMethod frameworkMethod, Object o) {
        System.out.println("in apply: " + this);
        return statement;
    }
}
