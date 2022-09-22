package ro.rasel.alibaba.threadlocal;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlRunnable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CompletableFutureWithThreadLocal {
    public static Executor executor = Executors.newFixedThreadPool(4);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        for (int i = 0; i < 10; i++) {
            final ThreadLocalWrapper threadLocalWrapper = new ThreadLocalWrapper();
            final TtlRunnable runnable = TtlRunnable.get(() -> System.out
                    .println(threadLocalWrapper.getString() + " " + threadLocalWrapper.getString2()));
            threadLocalWrapper.setString("hello" + i + "1 from main");
            threadLocalWrapper.setString2("hello" + i + "2 from main");
//            System.out.println(threadLocalWrapper.getString() + " " + threadLocalWrapper.getString2());
            CompletableFuture.runAsync(
                    runnable,
                    executor).get();
        }
    }

    public static class ThreadLocalWrapper {
        private static ThreadLocal<String> stringThreadLocal = new ThreadLocal<>();
        private static ThreadLocal<String> stringThreadLocal2 = new TransmittableThreadLocal<>();

        public String getString() {
            return stringThreadLocal.get();
        }

        public void setString(String s) {
            stringThreadLocal.set(s);
        }

        public String getString2() {
            return stringThreadLocal2.get();
        }

        public void setString2(String s) {
            stringThreadLocal2.set(s);
        }
    }
}
