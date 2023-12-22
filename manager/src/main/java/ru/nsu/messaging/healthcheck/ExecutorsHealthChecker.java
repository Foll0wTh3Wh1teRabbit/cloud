package ru.nsu.messaging.healthcheck;

import com.hazelcast.core.HazelcastInstance;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExecutorsHealthChecker {

    private final HazelcastInstance hazelcastInstance;

    public void healthCheckAccept() {

    }

    @Scheduled(cron = "* * * * * 0/30")
    public void checkDeadInstances() {
        log.info("Started check for dead instances");


    }

}
