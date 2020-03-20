package sh.platform.config;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

/**
 * A credential specialization that provides a kafka {@link Producer} and {@link Consumer}
 */
public class Kafka extends Credential {

    private static final String URL = "%s:%d";

    public Kafka(Map<String, Object> config) {
        super(config);
    }

    /**
     * Creates a Kafka producer.
     *
     * @param properties the properties where it will overwrite
     *                   {@link ProducerConfig#BOOTSTRAP_SERVERS_CONFIG} and set {@link LongSerializer}
     *                   and {@link StringSerializer} as key and value serializer respectively if absent.
     * @param <K>        the key type
     * @param <V>        the value type
     * @return a kafka {@link Producer}
     */
    public <K, V> Producer<K, V> getProducer(Map<String, Object> properties) {
        Objects.requireNonNull(properties, "properties is required");
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, getBroker());

        properties.putIfAbsent(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
        properties.putIfAbsent(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return new KafkaProducer<>(properties);

    }

    /**
     * Creates a Kafka Consumer.
     *
     * @param properties the properties Where it will overwrite
     *                   {@link ConsumerConfig#BOOTSTRAP_SERVERS_CONFIG} and set {@link LongDeserializer} and
     *                   {@link StringDeserializer} as key deserializer and value deserializer respectively
     *                   if absent.
     * @param <K>        the key type
     * @param <V>        the value type
     * @return a kafka {@link Consumer}
     */
    public <K, V> Consumer<K, V> getConsumer(Map<String, Object> properties, String... topicsNames) {
        Objects.requireNonNull(properties, "properties is required");

        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, getBroker());
        properties.putIfAbsent(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
        properties.putIfAbsent(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        Consumer<K, V> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Arrays.asList(topicsNames));
        return consumer;
    }

    private String getBroker() {
        return String.format(URL, getHost(), getPort());
    }
}
