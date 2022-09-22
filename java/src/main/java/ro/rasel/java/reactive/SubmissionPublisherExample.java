package ro.rasel.java.reactive;

import java.util.concurrent.Executors;
import java.util.concurrent.SubmissionPublisher;

public class SubmissionPublisherExample {
    public static void main(String... args) throws InterruptedException {
        MyTransformProcessor<Integer, Integer> transformer =
                new MyTransformProcessor<>(integer -> integer * 2);
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>(Executors.newFixedThreadPool(100), 1);
        publisher.subscribe(transformer);
        transformer.subscribe(new PrintSubscriber());
        for (int i = 0; i < 10; i++) {
            System.out.println("Publish " + i);
            transformer.submit(i);
        }

        transformer.close();
        publisher.close();
    }
}