package ru.nsu.cloud.model.health;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HealthCheckExecutorInformation {

    private String instanceId;

    private String instanceName;

}
