package ru.nsu.cloud.manager.selector.priority;

import com.hazelcast.map.IMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nsu.cloud.manager.selector.ExecutorSelector;
import ru.nsu.cloud.model.executor.ExecutorInformation;
import ru.nsu.cloud.model.task.Task;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class EqualTaskAssumptionExecutorSelector implements ExecutorSelector {

    @Override
    public String selectExecutor(Task task, IMap<String, ExecutorInformation> nodesMap) {
        Comparator<IMap.Entry<String, ExecutorInformation>> orderByPerformanceParams = (e1, e2) -> {
            int approximateAvailableCpuOnFirst = e1.getValue().getCpu() / (e1.getValue().getProcessesRunning() + 1);
            int approximateAvailableCpuOnSecond = e2.getValue().getCpu() / (e1.getValue().getProcessesRunning() + 1);
            int approximateAvailableGpuOnFirst = e1.getValue().getGpu() / (e1.getValue().getProcessesRunning() + 1);
            int approximateAvailableGpuOnSecond = e2.getValue().getGpu() / (e1.getValue().getProcessesRunning() + 1);

            if (approximateAvailableCpuOnFirst == approximateAvailableCpuOnSecond) {
                return approximateAvailableGpuOnFirst - approximateAvailableGpuOnSecond;
            }

            return approximateAvailableCpuOnFirst - approximateAvailableCpuOnSecond;
        };

        List<ExecutorInformation> orderedByPerformanceExecutors = nodesMap.entrySet()
            .stream()
            .sorted(orderByPerformanceParams)
            .map(Map.Entry::getValue)
            .toList();

        return orderedByPerformanceExecutors.stream()
            .filter(exec -> (exec.getCpu() / (exec.getProcessesRunning() + 1)) >= task.getCpu())
            .filter(exec -> (exec.getGpu() / (exec.getProcessesRunning() + 1)) >= task.getGpu())
            .findFirst()
            .orElse(orderedByPerformanceExecutors.get(orderedByPerformanceExecutors.size() - 1))
            .getAddress();
    }

}
