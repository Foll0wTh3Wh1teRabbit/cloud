package ru.nsu.cloud.model.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FunctionTaskWithId<T, V> {

    private FunctionTask<T, V> task;

    private String taskId;

}