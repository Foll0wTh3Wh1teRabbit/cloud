package ru.nsu.cloud.core.remote.loadbalance.impl;

import ru.nsu.cloud.core.remote.loadbalance.AbstractBalanceStrategy;
import ru.nsu.cloud.core.remote.loadbalance.exception.NoAvailableHostsException;
import java.util.List;

public class TimestampStrategyImpl extends AbstractBalanceStrategy {

    @Override
    public String getNextAddress() {
        List<String> availableHosts = hosts.stream()
            .filter(checkIfHostIsAvailable)
            .toList();

        if (availableHosts.isEmpty()) {
            throw new NoAvailableHostsException();
        }

        long index = System.currentTimeMillis() % availableHosts.size();

        return availableHosts.get((int) index);
    }

}
