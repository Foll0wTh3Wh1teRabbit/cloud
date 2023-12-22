package ru.nsu.cloud.model.health;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class HealthCheckManagerInformation {

    private String workingHost;

    private String workingPort;

    private Set<String> availableExecutors;

}
