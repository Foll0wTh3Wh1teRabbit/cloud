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
        log.trace("healthCheckAccept <- info: {}", healthCheckExecutorInformation);

        IMap<String, ExecutorInformation> healthCheckMap =
            hazelcastInstance.getMap(ManagerConfiguration.AVAILABLE_EXECUTORS_MAP);

        String instanceId = healthCheckExecutorInformation.getInstanceId();
        String instanceName = healthCheckExecutorInformation.getInstanceName();

        if (healthCheckMap.containsKey(instanceId)) {
            healthCheckMap.setTtl(instanceId, 30, TimeUnit.SECONDS);
        } else {
            healthCheckMap.set(
                instanceId,
                ExecutorInformation.builder().instanceId(instanceId).instanceName(instanceName).build(),
                30, TimeUnit.SECONDS
            );
        }

        log.trace("healthCheckAccept ->");
    }

}
