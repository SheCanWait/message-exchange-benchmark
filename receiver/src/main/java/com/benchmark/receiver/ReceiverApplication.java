package com.benchmark.receiver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@SpringBootApplication
public class ReceiverApplication {

	private static final RabbitReceiver rabbitReceiver = new RabbitReceiver();

	public static void main(String[] args) throws IOException, TimeoutException {
		SpringApplication.run(ReceiverApplication.class, args);

		rabbitReceiver.startReceivingRabbitMessages();

	}

}
