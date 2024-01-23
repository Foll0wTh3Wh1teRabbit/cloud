package ru.nsu.cloud.manager.task;

import com.hazelcast.core.HazelcastInstance;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nsu.cloud.model.exception.NoAvailableExecutorsException;
import ru.nsu.cloud.model.task.FunctionTask;
import ru.nsu.cloud.model.task.Task;
import ru.nsu.cloud.manager.configuration.ManagerConfiguration;
import ru.nsu.cloud.model.executor.ExecutorInformation;
import ru.nsu.cloud.model.task.TaskWithId;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    private final HazelcastInstance hazelcastInstance;

    private final TaskSender taskSender;

    public <T> void processTask(Task<T> task, String taskUuid) {
        log.info("processTask <- task: {}, id: {}", task, taskUuid);

        if (!areExecutorsForTaskExist(task.getInstanceTo())) {
            throw new NoAvailableExecutorsException();
        }

        taskSender.sendTask(
            TaskWithId.<T>builder()
                .task(task)
                .taskId(taskUuid)
                .build(),
            getRoutingKeyToSend(task.getInstanceTo(), true)
        );

        log.info("processTask -> ");
    }

    public <T, R> R processTaskAndReceiveResult(FunctionTask<T, R> task, String taskUuid) {
        log.info("processTaskAndReceiveResult <- task: {}, id: {}", task, taskUuid);

        R result = taskSender.sendTaskAndAwaitForResult(
            TaskWithId.<T>builder()
                .task(task)
                .taskId(taskUuid)
                .build(),
            getRoutingKeyToSend(task.getInstanceTo(), false)
        );

        log.info("processTaskAndReceiveResult -> result: {}", result);

        return result;
    }

    private boolean areExecutorsForTaskExist(String taskClass) {
        return hazelcastInstance.<String, ExecutorInformation> getMap(ManagerConfiguration.AVAILABLE_EXECUTORS_MAP)
            .values()
            .stream()
            .anyMatch(it -> it.getInstanceName().equals(taskClass));
    }

    private String getRoutingKeyToSend(String instanceTo, boolean isAsync) {
        return String.format("manager2%s%s", instanceTo, isAsync ? "Async" : "");
    }

}
