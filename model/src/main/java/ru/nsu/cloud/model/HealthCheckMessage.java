package ru.nsu.cloud.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HealthCheckMessage {

    private String workingHost;

    private String workingPort;

}
