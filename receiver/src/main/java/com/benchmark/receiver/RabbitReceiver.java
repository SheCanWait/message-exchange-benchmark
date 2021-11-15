package com.benchmark.receiver;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class RabbitReceiver {

    static Logger logger = LoggerFactory.getLogger(RabbitReceiver.class);

    private final static String QUEUE_NAME = "rabbit_test_queue";

    public void startReceivingRabbitMessages() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,
                false, false,false, null);
        logger.info("[!] Waiting for messages. To exit press Ctrl+C");

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body)
                    throws IOException {
                String message = new String(body, StandardCharsets.UTF_8);
                logger.info("[x] Message Recieved' " + null + "'");
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }

}
