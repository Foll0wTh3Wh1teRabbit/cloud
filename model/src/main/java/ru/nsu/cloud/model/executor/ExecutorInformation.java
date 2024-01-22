package ru.nsu.cloud.model.executor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ExecutorInformation implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String instanceId;

    private String instanceName;

    private List<String> runningTasks;

}
