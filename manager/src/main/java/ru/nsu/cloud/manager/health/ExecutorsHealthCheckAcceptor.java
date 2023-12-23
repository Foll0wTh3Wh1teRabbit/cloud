package ru.nsu.cloud.manager.health;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nsu.cloud.manager.configuration.ManagerConfiguration;
import ru.nsu.cloud.model.executor.ExecutorInformation;
import ru.nsu.cloud.model.health.HealthCheckExecutorInformation;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExecutorsHealthCheckAcceptor {

    private final HazelcastInstance hazelcastInstance;

    public void healthCheckAccept(HealthCheckExecutorInformation healthCheckExecutorInformation) {
        log.trace("healthCheckAccept <- info:{}", healthCheckExecutorInformation);

        IMap<String, ExecutorInformation> healthCheckMap = hazelcastInstance.getMap(ManagerConfiguration.AVAILABLE_EXECUTORS_MAP);

        String address = String.format(
            "%s:%s",
            healthCheckExecutorInformation.getWorkingHost(),
            healthCheckExecutorInformation.getWorkingPort()
        );

        if (healthCheckMap.containsKey(address)) {
            healthCheckMap.setTtl(address, 15, TimeUnit.SECONDS);
        } else {
            healthCheckMap.set(
                address,
                ExecutorInformation.builder()
                    .address(address)
                    .cpu(healthCheckExecutorInformation.getCpu())
                    .gpu(healthCheckExecutorInformation.getGpu())
                    .processesRunning(0)
                    .build(),
                15, TimeUnit.SECONDS
            );
        }

        log.trace("healthCheckAccept ->");
    }

}
