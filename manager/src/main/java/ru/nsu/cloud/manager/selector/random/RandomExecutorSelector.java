package ru.nsu.cloud.manager.selector.random;

import com.hazelcast.map.IMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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

    @Override
    public String selectExecutor(Task task, IMap<String, ExecutorInformation> nodesMap) {
        List<String> availableNodes = new ArrayList<>(nodesMap.keySet().stream().toList());

        if (availableNodes.isEmpty()) {
            throw new NoAvailableExecutorsException();
        }
        Collections.shuffle(availableNodes);

        return availableNodes.get(0);
    }

}
