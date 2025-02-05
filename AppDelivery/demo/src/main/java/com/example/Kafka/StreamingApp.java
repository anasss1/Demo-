package com.example.Kafka;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;

public class StreamingApp {

    public static void main(String[] args) {
        String inputTopic = "input_topic";
        String outputTopic = "output_topic";

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> stream = builder.stream(inputTopic);

        // Traitement de données en temps réel
        stream.mapValues(value -> value.toUpperCase())
              .to(outputTopic);

        KafkaStreams streams = new KafkaStreams(builder.build(), new java.util.Properties());
        streams.start();
    }
}
