package ru.nsu.cloud.model.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(builderMethodName = "functionTaskBuilder")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class FunctionTask<T, V> extends Task<T> {

    Class<V> returnType;

}
