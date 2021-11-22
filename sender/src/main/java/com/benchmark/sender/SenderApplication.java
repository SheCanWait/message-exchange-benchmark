package com.benchmark.sender;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@Slf4j
@SpringBootApplication
public class SenderApplication {

	private static final RabbitSender rabbitSender = new RabbitSender();
//	private static final KafkaSender kafkaSender = new KafkaSender();

	public static void main(String[] args) {
		SpringApplication.run(SenderApplication.class, args);

//		ApplicationContext context = new AnnotationConfigApplicationContext(ProducerChannelConfig.class);
		ConfigurableApplicationContext context = new SpringApplicationBuilder(SenderApplication.class).web(WebApplicationType.NONE).run(args);
		context.getBean(SenderApplication.class).run(context);
		context.close();

//		rabbitSender.executeRabbitTests(MessagePreparator.prepareMessage(100000000), 100, true);
//		rabbitSender.executeRabbitTests(MessagePreparator.prepareMessage(100000000), 100, false);

//		kafkaSender.executeKafkaTests("dupa1", 5);

	}

	private void run(ConfigurableApplicationContext context) {
		log.info("Inside ProducerApplication run method...");
		MessageChannel producerChannel = context.getBean("producerChannel", MessageChannel.class);


		Map<String, Object> headers = Collections.singletonMap(KafkaHeaders.TOPIC, "test-topic");
		producerChannel.send(new GenericMessage<>("dupa1234", headers));

		log.info("Finished ProducerApplication run method...");
	}

}
