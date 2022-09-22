package ro.rasel.alibaba.threadlocal;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlRunnable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TransmittableThreadLocalTest {
    private static final ExecutorService executorService = Executors.newFixedThreadPool(4);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // Fixed Thread Pool

        for (int i = 0; i < 10; i++) {
            ThreadLocalWrapper threadLocalWrapper = new ThreadLocalWrapper();
            int finalI = i;
            // The thread that generated the task assigns a value to the threadLocalWrapper
            ThreadLocalWrapper threadLocalWrapperMain = new ThreadLocalWrapper();
            threadLocalWrapperMain.setString2("hello2" + finalI);
            // Submit Tasks
            Runnable task = () -> {
                // Subthreads performing tasks
                System.out.println(Thread.currentThread().getName() + " execute task, name : " +
                        threadLocalWrapper.getString2());
            };
            // Extra processing to generate the decorated object ttlRunnable
            Runnable ttlRunnable = TtlRunnable.get(task);
            executorService.submit(ttlRunnable).get();

        }
    }

    static class ThreadLocalWrapper {
        private static ThreadLocal<String> threadLocal = new ThreadLocal<>();
        private static ThreadLocal<String> threadLocal2 = new TransmittableThreadLocal<>();

        public void setString(String s) {
            threadLocal.set(s);
        }

        public String getString() {
            return threadLocal.get();
        }

        public void setString2(String s) {
            threadLocal2.set(s);
        }

        public String getString2() {
            return threadLocal2.get();
        }
    }
}