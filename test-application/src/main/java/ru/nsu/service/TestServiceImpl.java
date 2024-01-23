package ru.nsu.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nsu.cloud.core.cluster.NsuCloudCluster;
import ru.nsu.cloud.model.task.FunctionTask;
import ru.nsu.cloud.model.task.MapReduceTask;
import ru.nsu.cloud.model.task.MapTask;
import ru.nsu.cloud.model.task.Task;

import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private static final String NUMBER_PROCESSOR = "NumberProcessor";

    private final NsuCloudCluster nsuCloudCluster;



    @Override
    public void executeRemote() {
        String id = nsuCloudCluster.deployAsyncTask(
            Task.<List<Integer>> taskBuilder()
                .instanceTo(NUMBER_PROCESSOR)
                .data(IntStream.range(1, 101).boxed().toList())
                .build()
        );

        log.info("executeRemote -> id: {}", id);
    }

    @Override
    public void executeRemoteWithValue() {
        Integer result = nsuCloudCluster.deployTask(
            FunctionTask.<List<Integer>, Integer> functionTaskBuilder()
                .instanceTo(NUMBER_PROCESSOR)
                .data(IntStream.range(1, 101).boxed().toList())
                .build()
        );

        log.info("executeRemoteWithValue -> res: {}", result);
    }

    @Override
    public void executeRemoteWithMap() {
        List<String> ids = nsuCloudCluster.deployMapTask(
            MapTask.<List<Integer>> mapTaskBuilder()
                .instanceTo(NUMBER_PROCESSOR)
                .data(IntStream.range(1, 101).boxed().toList())
                .mapFunction(new BatchCreator())
                .build()
        );

        log.info("executeRemoteWithMap -> ids: {}", ids);
    }

    @Override
    public void executeRemoteWithMapAndValue() {
        Integer result = nsuCloudCluster.deployMapReduceTask(
            MapReduceTask.<List<Integer>, Integer, Integer> mapReduceTaskBuilder()
                .instanceTo(NUMBER_PROCESSOR)
                .data(IntStream.range(1, 101).boxed().toList())
                .mapFunction(new BatchCreator())
                .reduceFunction(new SumFunction())
                .build()
        );

        log.info("executeRemoteWithMapAndValue -> res: {}", result);
    }

}
