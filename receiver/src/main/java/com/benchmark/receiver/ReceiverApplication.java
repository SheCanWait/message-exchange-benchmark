package com.benchmark.receiver;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@SpringBootApplication
public class ReceiverApplication {

	static Logger logger = LoggerFactory.getLogger(ReceiverApplication.class);

	private final static String QUEUE_NAME = "hello";

	public static void main(String[] args) throws IOException, TimeoutException {
		SpringApplication.run(ReceiverApplication.class, args);

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
				logger.info("[x] Message Recieved' " + message + "'");
			}
		};
		channel.basicConsume(QUEUE_NAME, true, consumer);
	}

}
