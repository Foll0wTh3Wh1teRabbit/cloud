package ru.nsu.service;

import ru.nsu.cloud.model.mapreduce.ReduceFunction;

import java.util.Collection;

public class SumFunction implements ReduceFunction<Integer, Integer> {

    @Override
    public Integer apply(Collection<Integer> ints) {
        return ints.stream().reduce(Integer::sum).get();
    }

}
