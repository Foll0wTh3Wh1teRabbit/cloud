package ru.nsu.cloud.model.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(builderMethodName = "resultBuilder")
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    private String taskId;

    private String instance;

}
