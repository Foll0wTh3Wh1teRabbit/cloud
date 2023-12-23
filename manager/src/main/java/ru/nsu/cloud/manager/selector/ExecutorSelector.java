package ru.nsu.cloud.manager.selector;

import ru.nsu.cloud.model.task.Task;

public interface ExecutorSelector {

    String selectExecutor(Task task);

}
