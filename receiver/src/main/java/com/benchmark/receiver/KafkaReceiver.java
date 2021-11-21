package com.benchmark.receiver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;

public class KafkaReceiver {

    @Value("kafka.groupId")
    public static final String GROUP_ID = "test-topic";
    public static final String KAFKA_TOPIC_NAME = "test-topic";

    @KafkaListener(topics = KAFKA_TOPIC_NAME, groupId = GROUP_ID)
    public void listenGroup(String message) {
        System.out.println("Received Message in group foo: " + message);
    }

}
