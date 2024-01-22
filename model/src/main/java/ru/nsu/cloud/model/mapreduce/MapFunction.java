package ru.nsu.cloud.model.mapreduce;

import java.util.Collection;
import java.util.function.Function;

public interface MapFunction<T> extends Function<T, Collection<T>> {
}
