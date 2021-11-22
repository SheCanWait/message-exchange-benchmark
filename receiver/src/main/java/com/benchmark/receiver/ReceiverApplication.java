package com.benchmark.receiver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.context.IntegrationFlowContext;
import org.springframework.integration.kafka.dsl.Kafka;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.PollableChannel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@Slf4j
@SpringBootApplication
public class ReceiverApplication {

	@Autowired
	PollableChannel consumerChannel;

	private static final RabbitReceiver rabbitReceiver = new RabbitReceiver();

	public static void main(String[] args) throws IOException, TimeoutException {
		SpringApplication.run(ReceiverApplication.class, args);

		ConfigurableApplicationContext context = new SpringApplicationBuilder(ReceiverApplication.class).web(WebApplicationType.NONE).run(args);
		List<String> topics = Arrays.asList("FANTASY", "HORROR", "ROMANCE", "THRILLER");
		context.getBean(ReceiverApplication.class).run(context, topics);
		context.close();

//		rabbitReceiver.startReceivingRabbitMessages();

		//kafka listener listens by default

	}

	private void run(ConfigurableApplicationContext context, List<String> topics) {
		log.info("Inside ProducerApplication run method...");
		PollableChannel consumerChannel = context.getBean("consumerChannel", PollableChannel.class);

		for (String topic : topics)
			addAnotherListenerForTopics(topic);

		Message<?> received = consumerChannel.receive();
		while (received != null) {
			received = consumerChannel.receive();
			//System.out.println("Received " + received.getPayload());
			log.info("Received" + received.getPayload());
		}
	}

	@Autowired
	private IntegrationFlowContext flowContext;

	@Autowired
	private KafkaProperties kafkaProperties;

	public void addAnotherListenerForTopics(String... topics) {
		Map<String, Object> consumerProperties = kafkaProperties.buildConsumerProperties();
		consumerProperties.put("group.id", "dummy");
		IntegrationFlow flow = IntegrationFlows
				.from(Kafka.messageDrivenChannelAdapter(
						new DefaultKafkaConsumerFactory<String, String>(consumerProperties), topics))
				.channel("consumerChannel").get();
		this.flowContext.registration(flow).register();
	}

}
