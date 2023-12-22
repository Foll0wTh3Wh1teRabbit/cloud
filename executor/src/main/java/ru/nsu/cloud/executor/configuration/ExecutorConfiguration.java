package ru.nsu.cloud.executor.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class ExecutorConfiguration {

    public static final String EXCHANGE = "cloudExchange";

    public static final String ROUTING_KEY = "health";

}
