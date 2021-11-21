package com.benchmark.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@SpringBootApplication
public class SenderApplication {

	private static final RabbitSender rabbitSender = new RabbitSender();
	private static final KafkaSender kafkaSender = new KafkaSender();

	public static void main(String[] args) {
		SpringApplication.run(SenderApplication.class, args);

//		rabbitSender.executeRabbitTests(MessagePreparator.prepareMessage(100000000), 100, true);
//		rabbitSender.executeRabbitTests(MessagePreparator.prepareMessage(100000000), 100, false);

		kafkaSender.executeKafkaTests("dupa1", 5);

	}

}
