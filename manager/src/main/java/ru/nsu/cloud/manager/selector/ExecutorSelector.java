package ru.nsu.cloud.manager.selector;

import com.hazelcast.map.IMap;
import ru.nsu.cloud.model.executor.ExecutorInformation;
import ru.nsu.cloud.model.task.Task;

public interface ExecutorSelector {

    String selectExecutor(Task task, IMap<String, ExecutorInformation>nodesMap);

}
