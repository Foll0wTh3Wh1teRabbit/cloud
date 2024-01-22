package ru.nsu.service;

import com.google.common.collect.Lists;
import ru.nsu.cloud.model.mapreduce.MapFunction;

import java.util.Collection;
import java.util.List;

public class BatchCreator implements MapFunction<List<Long>> {

    @Override
    public Collection<List<Long>> apply(List<Long> longs) {
        return Lists.partition(longs, 10);
    }

}
