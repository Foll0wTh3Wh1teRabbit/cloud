package ru.nsu.cloud.model.health;

import lombok.Builder;
import lombok.Data;
import ru.nsu.cloud.model.executor.ExecutorInformation;

import java.util.Collection;

@Data
@Builder
public class HealthCheckManagerInformation {

    private String workingHost;

    private String workingPort;

    private Collection<ExecutorInformation> availableExecutors;

}
