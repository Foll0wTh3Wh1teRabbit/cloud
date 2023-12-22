package ru.nsu.cloud.model.health;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HealthCheckNodeInformation {

    private String workingHost;

    private String workingPort;

}
