package ro.rasel.rabbitmq.helloworld.exchange.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ro.rasel.rabbitmq.helloworld.exchange.direct.DirectExchangeCommon.EXCHANGE_NAME;
import static ro.rasel.rabbitmq.helloworld.exchange.direct.DirectExchangeCommon.QUEUE_PREFIX;

public class DirectExchangeMessageConsumer {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        String instance = System.getenv("instance");
        String routingKeys = System.getenv("routingKeys");
        if (instance == null) {
            throw new IllegalStateException("No instance provided");
        }
        if (routingKeys==null){
            throw new IllegalStateException("No routingKeys provided");
        }
        final String queueName = QUEUE_PREFIX + instance;

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        try (Connection connection = factory.newConnection()) {
            try (Channel channel = connection.createChannel()) {
                channel.queueDeclare(queueName, true, false, false, null);
                for (String routingKey : Stream.of(routingKeys.split(","))
                        .map(String::trim)
                        .collect(Collectors.toList())) {
                    channel.queueBind(queueName, EXCHANGE_NAME, routingKey);
                }
                System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

                DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                    String message = new String(delivery.getBody(), "UTF-8");
                    System.out.println(" [x] Received '" + message + "'");
                };
                channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
                });
                Thread.sleep(300000);
            }
        }
    }
}
