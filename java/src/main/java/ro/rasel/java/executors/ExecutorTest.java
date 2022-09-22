//package ro.rasel.java.executors;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.BlockingDeque;
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.Future;
//import java.util.concurrent.LinkedBlockingDeque;
//import java.util.concurrent.RejectedExecutionHandler;
//import java.util.concurrent.SynchronousQueue;
//import java.util.concurrent.ThreadPoolExecutor;
//import java.util.concurrent.TimeUnit;
//
//public class ExecutorTest {
//    private final ThreadPoolExecutor threadPoolExecutor;
//
//    public ExecutorTest(ThreadPoolExecutor threadPoolExecutor) {
//        this.threadPoolExecutor = threadPoolExecutor;
//    }
//
//    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        new ExecutorTest(new ThreadPoolExecutor(10, 100, 1, TimeUnit.MINUTES, new SynchronousQueue<>(),
//                new RejectedExecutionHandler() {
//                    private final BlockingDeque blockingDeque = new LinkedBlockingDeque<>();
//
//                    @Override
//                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
//                        blockingDeque.put(r);
//                        executor.getQueue().put(BlockingQueue.get);
//                    }
//                }))
//                .test(100, 1000);
//    }
//
//    private void test(int threads, int delay) throws ExecutionException, InterruptedException {
//        List<Future<Void>> list = new ArrayList<>():
//        for (int i = 0; i < threads; i++) {
//            list.add(CompletableFuture.runAsync(() -> {
//                sleep(delay);
//                System.out.println("done " + Thread.currentThread());
//            }, threadPoolExecutor));
//        }
//        for (Future<Void> future : list) {
//            future.get();
//        }
//    }
//
//    private void sleep(int delay) {
//        try {
//            Thread.sleep(delay);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//}
