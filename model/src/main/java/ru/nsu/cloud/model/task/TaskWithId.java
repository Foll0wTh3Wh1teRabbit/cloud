package ru.nsu.cloud.model.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskWithId<T> {

    private Task<T> task;

    private String taskId;

}
