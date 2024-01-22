package ru.nsu.cloud.manager.resilience;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nsu.cloud.manager.configuration.ManagerConfiguration;
import ru.nsu.cloud.manager.task.TaskService;
import ru.nsu.cloud.model.executor.ExecutorInformation;
import ru.nsu.cloud.model.task.Task;

import javax.annotation.PostConstruct;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResilienceService {

    private final HazelcastInstance hazelcastInstance;

    private final TaskService taskService;

    @PostConstruct
    public void initHazelcastFeatures() {

    }

}
