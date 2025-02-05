package com.example.Kafka;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Properties;

public class DataConsumer {

    public static void main(String[] args) {
        String bootstrapServers = "localhost:9092";  // Adresse du broker Kafka
        String topic = "streaming_topic";            // Nom du topic Kafka
        String groupId = "streaming_group";          // Identifiant du groupe de consommateurs

        // Configuration du consommateur Kafka
        Properties properties = new Properties();
        properties.put("bootstrap.servers", bootstrapServers);
        properties.put("group.id", groupId);
        properties.put("key.deserializer", StringDeserializer.class.getName());
        properties.put("value.deserializer", StringDeserializer.class.getName());

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        // S'abonner au topic
        consumer.subscribe(Collections.singletonList(topic));

        try {
            while (true) {
                // Poll les messages
                consumer.poll(1000).forEach(record -> {
                    System.out.println("Consumed: " + record.value());
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            consumer.close();
        }
    }
}