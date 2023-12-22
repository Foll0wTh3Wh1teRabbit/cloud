package ru.nsu.cloud.manager.healthcheck;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nsu.cloud.manager.configuration.ManagerConfiguration;
import ru.nsu.cloud.model.HealthCheckMessage;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExecutorsHealthCheckAcceptor {

    private final HazelcastInstance hazelcastInstance;

    public void healthCheckAccept(HealthCheckMessage healthCheckMessage) {
        IMap<String, LocalDateTime> healthCheckMap =
            hazelcastInstance.getMap(ManagerConfiguration.EXECUTORS_HEALTH_CHECK_MAP);

        healthCheckMap.set(
            String.format("%s:%s", healthCheckMessage.getWorkingHost(), healthCheckMessage.getWorkingPort()),
            LocalDateTime.now(),
            15, TimeUnit.SECONDS
        );
    }

}
