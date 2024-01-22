package ru.nsu.cloud.core.cluster;

import ru.nsu.cloud.model.task.FunctionTask;
import ru.nsu.cloud.model.task.MapReduceTask;
import ru.nsu.cloud.model.task.MapTask;
import ru.nsu.cloud.model.task.Task;

import java.util.List;

public interface NsuCloudCluster {

    <T, R> R deployTask(FunctionTask<T, R> task);

    <T> String deployAsyncTask(Task<T> task);

    <T> List<String> deployMapTask(MapTask<T> mapTask);

    <T, V, R> R deployMapReduceTask(MapReduceTask<T, V, R> mapReduceTask);

}
