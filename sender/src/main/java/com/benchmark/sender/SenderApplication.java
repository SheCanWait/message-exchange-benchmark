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

	public static void main(String[] args) throws IOException, TimeoutException {
		SpringApplication.run(SenderApplication.class, args);

//		ApplicationContext context = new AnnotationConfigApplicationContext(ProducerChannelConfig.class);
		// below for kafka
//		ConfigurableApplicationContext context = new SpringApplicationBuilder(SenderApplication.class).web(WebApplicationType.NONE).run(args);
//		context.getBean(SenderApplication.class).run(context);
//		context.close();
		// above for kafka

//		StringBuilder str = new StringBuilder();
//		for (int i = 0; i < 39000; i++) {
//			str.append("AAAAAaaaaaAAAAAaaaaaAAA25");
//		}
//
//			str.append("message body 123");
//
//			byte[] bytes = str.toString().getBytes(StandardCharsets.UTF_8);
//
//			rabbitSender.executeRabbitTests(bytes, 5000, false);

//		rabbitSender.executeRabbitTests(bytes, 100, false);

		}

		private void run (ConfigurableApplicationContext context){
			MessageChannel producerChannel = context.getBean("producerChannel", MessageChannel.class);

			Map<String, Object> headers = Collections.singletonMap(KafkaHeaders.TOPIC, "test-topic");

			StringBuilder str = new StringBuilder();
			for (int i = 0; i < 39000; i++) {
				str.append("AAAAAaaaaaAAAAAaaaaaAAA25");
			}

			log.info("start sending");
			for (int i = 0; i < 5000; i++) {
				String str1 = str.toString();
				String str11 = " message nr " + (i + 1);
				str1 += str11;
				producerChannel.send(new GenericMessage<>(str1, headers));
				log.info(str11 + " sent");
			}
			log.info("stop sending");
		}

}
