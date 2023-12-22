package ru.nsu.cloud.manager.healthcheck;

import com.hazelcast.core.HazelcastInstance;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.cloud.manager.configuration.ManagerConfiguration;

import java.net.InetAddress;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HealthCheckController {

    private final HazelcastInstance hazelcastInstance;

    @Value("${server.port}")
    private String port;

    @PostMapping("/health")
    public ResponseEntity<String> healthCheck() {
        log.info(
            "healthCheck <- received on host:{}, port:{}, availableExecutors:{}",
            InetAddress.getLoopbackAddress().getHostName(),
            port,
            hazelcastInstance.<String, LocalDateTime> getMap(ManagerConfiguration.EXECUTORS_HEALTH_CHECK_MAP).keySet()
        );

        return ResponseEntity.ok(null);
    }

}
