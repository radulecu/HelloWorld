package ro.rasel.rabbitmq.helloworld.queue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

public class QueueMessageConsumer {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(16);
        try {
            final Random random = new Random();
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            factory.setPort(5672);
            try (Connection connection = factory.newConnection()) {
                try (Channel channel = connection.createChannel()) {
                    channel.queueDeclare(QueueCommon.QUEUE_NAME, true, false, false, null);
                    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

                    DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                        executor.execute(() -> {
                            try {
                                String message = new String(delivery.getBody(), "UTF-8");
                                System.out.println(" [x] Received '" + message + "'." + "is redeliver " +
                                        delivery.getEnvelope().isRedeliver() + ".");
                                if (random.nextBoolean()) {
                                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                                } else {
                                    channel.basicReject(delivery.getEnvelope().getDeliveryTag(), true);
                                }
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                System.out.println(" [y] Received '" + message + "'." + "is redeliver " +
                                        delivery.getEnvelope().isRedeliver() + ".");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                    };
                    channel.basicConsume(QueueCommon.QUEUE_NAME, false, deliverCallback, consumerTag -> {
                    });
                    Thread.sleep(300000);
                }
            }
        } finally {
            executor.shutdown();
        }
    }
}
