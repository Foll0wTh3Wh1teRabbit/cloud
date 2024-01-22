package ru.nsu.cloud.model.mapreduce;

import java.util.Collection;
import java.util.function.Function;

public interface ReduceFunction<V, T> extends Function<Collection<V>, T> {
}
