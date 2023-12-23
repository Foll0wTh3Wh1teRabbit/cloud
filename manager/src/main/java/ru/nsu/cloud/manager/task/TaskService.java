package ru.nsu.cloud.manager.task;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nsu.cloud.manager.configuration.ManagerConfiguration;
import ru.nsu.cloud.manager.selector.ExecutorSelector;
import ru.nsu.cloud.model.executor.ExecutorInformation;
import ru.nsu.cloud.model.task.Task;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    private final ExecutorSelector equalTaskAssumptionExecutorSelector;

    private final HazelcastInstance hazelcastInstance;

    public void processTask(Task task) {
        log.info("processTask <- task:{}", task);

        String selectedNode = executorNodeSelection(task);

        // TODO pass to executor
    }

    private String executorNodeSelection(Task task) {
        hazelcastInstance.getCPSubsystem().getLock(ManagerConfiguration.EXECUTOR_SELECTION_LOCK).lock();
        log.info("executorNodeSelection <- task:{}, lock acquired", task);

        IMap<String, ExecutorInformation> nodesMap = hazelcastInstance.getMap(ManagerConfiguration.AVAILABLE_EXECUTORS_MAP);

        String selectedExecutorNode = equalTaskAssumptionExecutorSelector.selectExecutor(task, nodesMap);

        Integer currentProcessesValue = nodesMap.get(selectedExecutorNode).getProcessesRunning();
        ExecutorInformation updatedExecutorInformation = nodesMap.get(selectedExecutorNode)
            .toBuilder()
            .processesRunning(currentProcessesValue + 1)
            .build();

        nodesMap.set(selectedExecutorNode, updatedExecutorInformation);

        log.info("executorNodeSelection -> executor:{}, lock releasing", selectedExecutorNode);
        hazelcastInstance.getCPSubsystem().getLock(ManagerConfiguration.EXECUTOR_SELECTION_LOCK).unlock();

        return selectedExecutorNode;
    }

}
