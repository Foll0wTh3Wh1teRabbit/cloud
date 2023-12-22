package ru.nsu.cloud.manager.configuration;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ManagerConfiguration {

    public static final String AVAILABLE_EXECUTORS_MAP = "availableExecutors";

    @Bean
    public Config config(){
        return new Config();
    }

    @Bean
    public HazelcastInstance hazelcastInstance(Config config){
        return Hazelcast.newHazelcastInstance(config);
    }

}
