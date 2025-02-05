import org.junit.jupiter.api.*;
import org.testcontainers.containers.KafkaContainer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class KafkaEndToEndTest {

    private static KafkaContainer kafkaContainer;

    @BeforeAll
    public static void setUp() {
        // Démarrage du conteneur Kafka
        kafkaContainer = new KafkaContainer("confluentinc/cp-kafka:latest");
        kafkaContainer.start();
    }

    @Test
    public void testKafkaProducerAndConsumer() throws InterruptedException {
        String bootstrapServers = kafkaContainer.getBootstrapServers();
        String topic = "test-topic";

        // Créer le producteur Kafka
        Properties producerProps = new Properties();
        producerProps.put("bootstrap.servers", bootstrapServers);
        producerProps.put("key.serializer", StringSerializer.class.getName());
        producerProps.put("value.serializer", StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(producerProps);

        // Envoyer un message
        String message = "Test message";
        producer.send(new ProducerRecord<>(topic, "key", message));
        producer.flush();

        // Créer le consommateur Kafka
        Properties consumerProps = new Properties();
        consumerProps.put("bootstrap.servers", bootstrapServers);
        consumerProps.put("group.id", "test-group");
        consumerProps.put("key.deserializer", StringDeserializer.class.getName());
        consumerProps.put("value.deserializer", StringDeserializer.class.getName());

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProps);
        consumer.subscribe(java.util.Collections.singletonList(topic));

        // Consommer le message
        boolean messageReceived = false;
        for (int i = 0; i < 10; i++) {
            var records = consumer.poll(java.time.Duration.ofMillis(1000));
            for (var record : records) {
                if (record.value().equals(message)) {
                    messageReceived = true;
                    break;
                }
            }
            if (messageReceived) break;
        }

        // Vérifier que le message a été consommé
        assertTrue(messageReceived);

        consumer.close();
        producer.close();
    }

    @AfterAll
    public static void tearDown() {
        // Arrêter le conteneur Kafka après les tests
        kafkaContainer.stop();
    }
}
