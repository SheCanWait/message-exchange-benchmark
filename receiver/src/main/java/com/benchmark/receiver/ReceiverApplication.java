package com.benchmark.receiver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
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
import org.springframework.integration.kafka.inbound.KafkaMessageDrivenChannelAdapter;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.PollableChannel;

import java.io.IOException;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeoutException;

@Slf4j
@SpringBootApplication
public class ReceiverApplication {

	@Autowired
	PollableChannel consumerChannel;

//	private static final RabbitReceiver rabbitReceiver = new RabbitReceiver();

	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
//		SpringApplication.run(ReceiverApplication.class, args);

		// uncomment below to run graphQL
		ConfigurableApplicationContext context = SpringApplication.run(ReceiverApplication.class, args);
		GraphQLRequestorAndReceiver client = (GraphQLRequestorAndReceiver) context.getBean("graphQLRequestorAndReceiver");
		client.request(100);
		// uncomment above to run graphQL

		//uncomment below to test REST
//		RestResponseRequestorAndReceiver restResponseRequestorAndReceiver = new RestResponseRequestorAndReceiver();
//			restResponseRequestorAndReceiver.requestAndReceive(5000);

		//uncomment above to test REST

		//uncomment below to run kafka
//		KafkaConsumerExample.runConsumer();
		//uncomment above to run kafka

//		rabbitReceiver.startReceivingRabbitMessages();

		//kafka listener listens by default

	}
//
//
//	public static Consumer<Long, String> createConsumer() {
//		Properties props = new Properties();
//		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//		props.put(ConsumerConfig.GROUP_ID_CONFIG, "dummy");
//		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
//		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
//		props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1000);
//		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
////		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, 100);
//
//		Consumer<Long, String> consumer = new KafkaConsumer<>(props);
//		consumer.subscribe(Collections.singletonList("test-topic"));
//		return consumer;
//	}
//
//	static void runConsumer() {
//		Consumer<Long, String> consumer = createConsumer();
//
//		int noMessageFound = 0;
//
//		while (true) {
//			ConsumerRecords<Long, String> consumerRecords = consumer.poll(Duration.ofMillis(15000));
//			// 1000 is the time in milliseconds consumer will wait if no record is found at broker.
//			consumerRecords.forEach(record -> {
//				log.info("Record Key " + record.key());
//				log.info("Record value " + record.value());
//				log.info("Record partition " + record.partition());
//				log.info("Record offset " + record.offset());
//			});
//			if (consumerRecords.count() == 0) {
//				noMessageFound++;
//				if (noMessageFound > 100)
//					// If no message found count is reached to threshold exit loop.
//					break;
//				else
//					continue;
//			}
//
//			//print each record.
//			consumerRecords.forEach(record -> {
//				log.info("Record Key " + record.key());
//				log.info("Record value " + record.value());
//				log.info("Record partition " + record.partition());
//				log.info("Record offset " + record.offset());
//			});
//
//			// commits the offset of record to broker.
//			consumer.commitAsync();
//		}
//		consumer.close();
//	}
//
//	private void run(ConfigurableApplicationContext context, List<String> topics) {
//		log.info("Inside ProducerApplication run method...");
//		KafkaMessageDrivenChannelAdapter<String, String> kafkaMessageDrivenChannelAdapter = context.getBean("kafkaMessageDrivenChannelAdapter", KafkaMessageDrivenChannelAdapter.class);
//		PollableChannel consumerChannel = (PollableChannel) kafkaMessageDrivenChannelAdapter.getOutputChannel();
//		for (String topic : topics)
//			addAnotherListenerForTopics(topic);
//
//		Message<?> received = consumerChannel.receive(1000);
//		int x =5;
//		while (true) {
//			received = consumerChannel.receive(20000);
//			//System.out.println("Received " + received.getPayload());
//			if (received != null) {
//				log.info("Received" + received.getPayload());
//
//			} else {
//				log.info("timeout blablabla");
//			}
//
//		}
//	}

//	@Autowired
//	private IntegrationFlowContext flowContext;
//
//	@Autowired
//	private KafkaProperties kafkaProperties;

//	public void addAnotherListenerForTopics(String... topics) {
//		Map<String, Object> consumerProperties = kafkaProperties.buildConsumerProperties();
//		consumerProperties.put("group.id", "dummy");
//		IntegrationFlow flow = IntegrationFlows
//				.from(Kafka.messageDrivenChannelAdapter(
//						new DefaultKafkaConsumerFactory<String, String>(consumerProperties), topics))
//				.channel("consumerChannel").get();
//		this.flowContext.registration(flow).register();
//	}

}
