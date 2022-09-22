package ro.rasel.rabbitmq.helloworld.exchange.direct;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.text.MessageFormat;
import java.util.UUID;

import static ro.rasel.rabbitmq.helloworld.exchange.direct.DirectExchangeCommon.EXCHANGE_NAME;

public class DirectExchangeMessageProducer {
    public static void main(String[] argv) throws Exception {
        String routingKey = System.getenv("routingKey");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT, true, false, false, null);

            final UUID clientUuid = UUID.randomUUID();
            for (int i = 1; ; i++) {
                String message = MessageFormat.format("Hello World! Message {0} for client {1} with routing key {2}", i,
                        clientUuid, routingKey);
                channel.basicPublish(EXCHANGE_NAME, routingKey,
                        new AMQP.BasicProperties.Builder().deliveryMode(2).build(),
                        message.getBytes());
                System.out.println(" [x] Sent '" + message + "'");
                Thread.sleep(1000);
            }
        }
    }
}
