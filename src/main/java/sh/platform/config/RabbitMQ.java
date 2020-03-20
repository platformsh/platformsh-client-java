package sh.platform.config;


import com.rabbitmq.jms.admin.RMQConnectionFactory;

import javax.jms.ConnectionFactory;
import java.util.Map;
import java.util.function.Supplier;

/**
 * A credential specialization that provides a {@link ConnectionFactory} using RabbitMQ
 */
public class RabbitMQ extends Credential implements Supplier<ConnectionFactory> {

    public RabbitMQ(Map<String, Object> config) {
        super(config);
    }

    @Override
    public ConnectionFactory get() {
        RMQConnectionFactory connectionFactory = new RMQConnectionFactory();
        connectionFactory.setUsername(getString("username"));
        connectionFactory.setPassword(getString("password"));
        connectionFactory.setHost(getString("host"));
        connectionFactory.setPort(getPort());
        return connectionFactory;
    }
}
