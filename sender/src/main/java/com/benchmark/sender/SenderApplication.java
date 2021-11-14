package com.benchmark.sender;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@SpringBootApplication
public class SenderApplication {

	private final static String QUEUE_NAME = "hello";
	static Logger logger
			= LoggerFactory.getLogger(SenderApplication.class);

	public static void main(String[] args) throws IOException, TimeoutException {
		SpringApplication.run(SenderApplication.class, args);
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME,
				false,
				false,
				false,
				null);
		String message = "Welcome to RabbitMQ 33";
		channel.basicPublish("",
				QUEUE_NAME,
				null,
				message.getBytes(StandardCharsets.UTF_8));
		logger.debug("[!] Sent '" + message + "'");
		channel.close();
		connection.close();
	}

}
