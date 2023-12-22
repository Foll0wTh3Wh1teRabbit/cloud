package ru.nsu.cloud.core.remote.loadbalance.impl;

import ru.nsu.cloud.core.remote.loadbalance.AbstractBalanceStrategy;
import ru.nsu.cloud.core.remote.loadbalance.util.CyclicQueue;
import ru.nsu.cloud.core.remote.loadbalance.BalanceStrategy;

import java.util.List;

public class RoundRobinStrategyImpl extends AbstractBalanceStrategy {

    private CyclicQueue<String> hostsQueue;

    @Override
    public String getNextHost() {
        return hostsQueue.getNext();
    }

    @Override
    public BalanceStrategy instantiate(List<String> hosts) {
        this.hostsQueue = new CyclicQueue<>(hosts);

        return this;
    }

}
