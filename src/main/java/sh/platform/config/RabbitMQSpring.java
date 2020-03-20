package sh.platform.config;

import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

import java.util.Map;
import java.util.function.Supplier;

/**
 * A credential specialization that provides a {@link JmsListenerContainerFactory} using RabbitMQ
 */
public class RabbitMQSpring extends Credential implements Supplier<JmsListenerContainerFactory> {

    public RabbitMQSpring(Map<String, Object> config) {
        super(config);
    }

    @Override
    public JmsListenerContainerFactory get() {
        RabbitMQ rabbitMQ = new RabbitMQ(config);
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(rabbitMQ.get());
        return factory;
    }
}
