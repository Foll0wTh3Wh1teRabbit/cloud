package ru.nsu.cloud.manager.selector.priority;

import com.hazelcast.core.HazelcastInstance;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nsu.cloud.manager.selector.ExecutorSelector;
import ru.nsu.cloud.model.task.Task;

@Slf4j
@Service
@RequiredArgsConstructor
public class PriorityExecutorSelector implements ExecutorSelector {

    private final HazelcastInstance hazelcastInstance;

    @Override
    public String selectExecutor(Task task) {
        return null;
    }

}
