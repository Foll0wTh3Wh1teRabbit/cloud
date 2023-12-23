package ru.nsu.cloud.manager.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nsu.cloud.manager.selector.ExecutorSelector;
import ru.nsu.cloud.model.task.Task;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    private final ExecutorSelector randomExecutorSelector;

    public void processTask(Task task) {
        log.info("processTask <- task:{}", task);

        String selectedNode = randomExecutorSelector.selectExecutor(task);

        log.info("Selected node:{} for task:{}", selectedNode, task);

        // TODO pass to executor
    }

}
