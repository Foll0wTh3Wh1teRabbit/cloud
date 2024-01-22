package ru.nsu.cloud.model.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

@Data
@SuperBuilder(builderMethodName = "taskBuilder")
@NoArgsConstructor
@AllArgsConstructor
public class Task<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String instanceTo;

    private T data;

    private String serializedData;

}
