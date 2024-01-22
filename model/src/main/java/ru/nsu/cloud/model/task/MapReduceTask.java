package ru.nsu.cloud.model.task;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import ru.nsu.cloud.model.mapreduce.MapFunction;
import ru.nsu.cloud.model.mapreduce.ReduceFunction;

@Data
@SuperBuilder(builderMethodName = "mapReduceTaskBuilder")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class MapReduceTask<T, V, R> extends FunctionTask<T, V> {

    @JsonIgnore
    private MapFunction<T> mapFunction;

    @JsonIgnore
    private ReduceFunction<V, R> reduceFunction;

}
