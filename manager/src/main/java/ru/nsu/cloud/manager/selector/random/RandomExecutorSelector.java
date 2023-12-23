package ru.nsu.cloud.manager.selector.random;

import com.hazelcast.core.HazelcastInstance;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nsu.cloud.manager.configuration.ManagerConfiguration;
import ru.nsu.cloud.manager.selector.ExecutorSelector;
import ru.nsu.cloud.model.exception.NoAvailableExecutorsException;
import ru.nsu.cloud.model.executor.ExecutorInformation;
import ru.nsu.cloud.model.task.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RandomExecutorSelector implements ExecutorSelector {

    private final HazelcastInstance hazelcastInstance;

    @Override
    public String selectExecutor(Task task) {
        List<String> availableNodes = new ArrayList<>(
            hazelcastInstance.<String, ExecutorInformation> getMap(ManagerConfiguration.AVAILABLE_EXECUTORS_MAP)
                .keySet().stream().toList()
        );

        if (availableNodes.isEmpty()) {
            throw new NoAvailableExecutorsException();
        }

        Collections.shuffle(availableNodes);

        return availableNodes.get(0);
    }

}
