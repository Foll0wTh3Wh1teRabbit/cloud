package ru.nsu.cloud.core.remote.loadbalance.util;

import java.util.ArrayDeque;
import java.util.List;

public class CyclicQueue<T> {

    private final ArrayDeque<T> queue;

    public CyclicQueue(List<T> list) {
        this.queue = new ArrayDeque<>(list);
    }

    public T getNext() {
        if (this.queue.isEmpty()) {
            return null;
        }

        T elem = queue.pollFirst();
        queue.addLast(elem);

        return elem;
    }

}
