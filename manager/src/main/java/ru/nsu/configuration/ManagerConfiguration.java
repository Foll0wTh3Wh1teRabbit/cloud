package ru.nsu.configuration;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class ManagerConfiguration {

    @Bean
    public Config config(){
        return new Config();
    }

    @Bean
    public HazelcastInstance hazelcastInstance(Config config){
        return Hazelcast.newHazelcastInstance();
    }

}
