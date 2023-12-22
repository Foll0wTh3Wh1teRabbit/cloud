package ru.nsu.cloud.executor.health;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;

@Slf4j
@RestController
public class HealthCheckController {

    @Value("${server.port}")
    private String port;

    @PostMapping("/health")
    public ResponseEntity<String> healthCheck() {
        log.info("healthCheck <- received on host:{}, port:{}", InetAddress.getLoopbackAddress().getHostName(), port);

        return ResponseEntity.ok(null);
    }

}
