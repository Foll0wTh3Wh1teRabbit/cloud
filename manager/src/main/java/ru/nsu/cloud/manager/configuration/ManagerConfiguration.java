package ru.nsu.cloud.manager.configuration;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ManagerConfiguration {

    public static final String AVAILABLE_EXECUTORS_MAP = "executors";

    public static final String PROCESSING_TASKS_MAP = "tasks";

    public static final String EXECUTORS_TO_TASKS = "executorsToTasks";

    public static final String LOST_TASKS = "lostTasks";

    public static final String EXCHANGE = "cloudExchange";

    public static final String UPDATE_TASKS_LOCK = "updateTasksLock";

    @Bean
    public Config config() {
        return new Config();
    }

    @Bean
    public HazelcastInstance hazelcastInstance(Config config) {
        return Hazelcast.newHazelcastInstance(config);
    }

}
