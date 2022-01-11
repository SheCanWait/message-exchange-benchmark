package com.benchmark.sender;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

public class RabbitSender {

    private final static String QUEUE_NAME = "rabbit_test_queue";
    static Logger logger = LoggerFactory.getLogger(RabbitSender.class);

    public void executeRabbitTests(byte[] message, int numberOfRepetitions, boolean useSeparatedThreads) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,
                false,
                false,
                false,
                null);

        if(useSeparatedThreads) {
            for(int i = 0 ; i < numberOfRepetitions ; i++) {
                int finalI = i;
                new Thread(() -> {
                    try {
                        channel.basicPublish("",
                                QUEUE_NAME,
                                null,
                                message);
                        logger.info("[!] Sent '" + finalI + "'");
                        channel.close();
                        connection.close();
                    } catch (IOException | TimeoutException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } else {
                for(int i = 0 ; i < numberOfRepetitions ; i++) {
//                    logger.info("[!] Sent '" + (i + 1) + "'");
                            channel.basicPublish("",
                                    QUEUE_NAME,
                                    null,
                                    message);
                            logger.info("[!] Sent '" + (i + 1) + "'");
                }
        }
    }

}
