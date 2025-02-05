package com.example.Kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class DataProducer {

    public static void main(String[] args) {
        String bootstrapServers = "localhost:9092";  // Adresse du broker Kafka
        String topic = "streaming_topic";            // Nom du topic Kafka

        // Configuration du producteur Kafka
        Properties properties = new Properties();
        properties.put("bootstrap.servers", bootstrapServers);
        properties.put("key.serializer", StringSerializer.class.getName());
        properties.put("value.serializer", StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        try {
            for (int i = 0; i < 100; i++) {
                String message = "Data record " + i;
                ProducerRecord<String, String> record = new ProducerRecord<>(topic, "key-" + i, message);
                producer.send(record);
                System.out.println("Sent: " + message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }
    }
}
