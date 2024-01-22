package ru.nsu.cloud.model.task;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Collection;
import java.util.function.Function;

@Data
@SuperBuilder(builderMethodName = "mapTaskBuilder")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class MapTask<T> extends Task<T> {

    @JsonIgnore
    private Function<T, Collection<T>> mapFunction;

}
