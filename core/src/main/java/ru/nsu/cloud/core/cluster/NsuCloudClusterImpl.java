package ru.nsu.cloud.core.cluster;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nsu.cloud.core.client.NsuCloudClusterHttpClient;
import ru.nsu.cloud.core.loadbalance.LoadBalancer;
import ru.nsu.cloud.model.task.FunctionTask;
import ru.nsu.cloud.model.task.MapReduceTask;
import ru.nsu.cloud.model.task.MapTask;
import ru.nsu.cloud.model.task.Task;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NsuCloudClusterImpl implements NsuCloudCluster {

    private final NsuCloudClusterHttpClient nsuCloudClusterHttpClient;

    private final LoadBalancer loadBalancer;

    private final ObjectMapper objectMapper;

    @Override
    public <T, R> R deployTask(FunctionTask<T, R> task) {
        log.info("deployTask <- task: {}", task);

        String hostToSendTask = chooseHost();

        try {
            task.setSerializedData(objectMapper.writeValueAsString(task.getData()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        R result = nsuCloudClusterHttpClient.sendTask(task, hostToSendTask);

        log.info("deployTask -> result: {}", result);

        return result;
    }

    @Override
    public <T> String deployAsyncTask(Task<T> task) {
        log.info("deployTaskAsync <- task: {}", task);

        String hostToSendTask = chooseHost();

        try {
            task.setSerializedData(objectMapper.writeValueAsString(task.getData()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String taskId = nsuCloudClusterHttpClient.sendTaskAsync(task, hostToSendTask);

        log.info("deployTaskAsync -> id: {}", taskId);

        return taskId;
    }

    @Override
    public <T> List<String> deployMapTask(MapTask<T> mapTask) {
        log.info("deployMapTask <- task: {}", mapTask);

        return mapTask.getMapFunction().apply(mapTask.getData())
            .stream()
            .map(it -> deployAsyncTask(
                Task.taskBuilder()
                    .instanceTo(mapTask.getInstanceTo())
                    .data(it)
                    .build()
            ))
            .toList();
    }

    @Override
    public <T, V, R> R deployMapReduceTask(MapReduceTask<T, V, R> mapReduceTask) {
        log.info("deployMapReduceTask <- task: {}", mapReduceTask);

        List<V> results = mapReduceTask.getMapFunction().apply(mapReduceTask.getData())
            .stream()
            .map(it -> deployTask(
                FunctionTask.<T, V> functionTaskBuilder()
                    .instanceTo(mapReduceTask.getInstanceTo())
                    .data(it)
                    .build()
            )).toList();

        return mapReduceTask.getReduceFunction().apply(results);
    }

    private String chooseHost() {
        String hostToSendTask = loadBalancer.getAvailableHost();

        log.info("Chosen host {} to manage execution", hostToSendTask);

        return hostToSendTask;
    }

}
