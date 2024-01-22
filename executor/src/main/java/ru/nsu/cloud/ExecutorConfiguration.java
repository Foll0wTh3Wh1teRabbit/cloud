package ru.nsu.cloud;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class ExecutorConfiguration {

    public static final String EXCHANGE = "cloudExchange";

    public static final String EXECUTOR_2_MANAGER_QUEUE = "executor2manager";

    @Value("${nsucloud.executor.instanceName}")
    private String instanceName;



    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost", 5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");

        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }



    @Bean
    Queue queue2Executor() {
        Queue queue2Executor = new Queue(String.format("manager2%s", instanceName), false);

        amqpAdmin().declareQueue(queue2Executor);

        return queue2Executor;
    }

    @Bean
    Queue queue2ExecutorAsync() {
        Queue queue2ExecutorAsync = new Queue(String.format("manager2%sAsync", instanceName), false);

        amqpAdmin().declareQueue(queue2ExecutorAsync);

        return queue2ExecutorAsync;
    }

    @Bean
    DirectExchange cloudExchange() {
        return new DirectExchange(EXCHANGE, false, false);
    }

    @Bean
    Binding bindingQueue2Executor(Queue queue2Executor, DirectExchange cloudExchange) {
        Binding bindingQueue2Executor = BindingBuilder.bind(queue2Executor)
            .to(cloudExchange)
            .with(String.format("manager2%s", instanceName));

        amqpAdmin().declareBinding(bindingQueue2Executor);

        return bindingQueue2Executor;
    }

    @Bean
    Binding bindingQueue2ExecutorAsync(Queue queue2ExecutorAsync, DirectExchange cloudExchange) {
        Binding bindingQueue2ExecutorAsync = BindingBuilder.bind(queue2ExecutorAsync)
            .to(cloudExchange)
            .with(String.format("manager2%sAsync", instanceName));

        amqpAdmin().declareBinding(bindingQueue2ExecutorAsync);

        return bindingQueue2ExecutorAsync;
    }

}
