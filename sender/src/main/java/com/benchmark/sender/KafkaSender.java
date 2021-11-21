package com.benchmark.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import static com.benchmark.sender.KafkaTopicConfig.KAFKA_TOPIC_NAME;

public class KafkaSender {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void executeKafkaTests(String message, int numberOfRepetitions) {

        for(int i = 0 ; i < numberOfRepetitions ; i++) {
            kafkaTemplate.send(KAFKA_TOPIC_NAME, message);
        }

    }

}
