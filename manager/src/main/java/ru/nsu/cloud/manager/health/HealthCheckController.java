package ru.nsu.cloud.manager.health;

import com.hazelcast.core.HazelcastInstance;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.cloud.manager.configuration.ManagerConfiguration;
import ru.nsu.cloud.model.health.HealthCheckManagerInformation;

import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HealthCheckController {

    private final HazelcastInstance hazelcastInstance;

    @Value("${server.port}")
    private String port;

    @PostMapping("/health")
    public ResponseEntity<HealthCheckManagerInformation> healthCheck() {
        log.info("healthCheck <- received on host:{}, port:{}", InetAddress.getLoopbackAddress().getHostName(), port);

        Set<String> availableExecutors =
            hazelcastInstance.<String, LocalDateTime> getMap(ManagerConfiguration.AVAILABLE_EXECUTORS_MAP).keySet();

        return ResponseEntity.ok(
            HealthCheckManagerInformation.builder()
                .workingHost(InetAddress.getLoopbackAddress().getHostName())
                .workingPort(port)
                .availableExecutors(availableExecutors)
                .build()
        );
    }

}
