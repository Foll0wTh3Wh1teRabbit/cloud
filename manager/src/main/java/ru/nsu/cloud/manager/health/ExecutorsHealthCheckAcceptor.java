package ru.nsu.cloud.manager.health;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nsu.cloud.manager.configuration.ManagerConfiguration;
import ru.nsu.cloud.model.health.HealthCheckNodeInformation;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExecutorsHealthCheckAcceptor {

    private final HazelcastInstance hazelcastInstance;

    public void healthCheckAccept(HealthCheckNodeInformation healthCheckNodeInformation) {
        IMap<String, LocalDateTime> healthCheckMap =
            hazelcastInstance.getMap(ManagerConfiguration.AVAILABLE_EXECUTORS_MAP);

        healthCheckMap.set(
            String.format("%s:%s", healthCheckNodeInformation.getWorkingHost(), healthCheckNodeInformation.getWorkingPort()),
            LocalDateTime.now(),
            15, TimeUnit.SECONDS
        );
    }

}
