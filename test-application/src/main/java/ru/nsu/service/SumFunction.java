package ru.nsu.service;

import ru.nsu.cloud.model.mapreduce.ReduceFunction;

import java.util.Collection;

public class SumFunction implements ReduceFunction<Long, Long> {

    @Override
    public Long apply(Collection<Long> longs) {
        return longs.stream().map(Long.class::cast).reduce(Long::sum).get();
    }

}
