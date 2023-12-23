package ru.nsu.cloud.core.remote.loadbalance.impl;

import ru.nsu.cloud.core.remote.loadbalance.AbstractBalanceStrategy;
import ru.nsu.cloud.model.exception.NoAvailableManagersException;

import java.util.List;

public class TimestampStrategyImpl extends AbstractBalanceStrategy {

    @Override
    public String getNextAddress() {
        List<String> availableHosts = hosts.stream()
            .filter(checkIfHostIsAvailable)
            .toList();

        if (availableHosts.isEmpty()) {
            throw new NoAvailableManagersException();
        }

        long index = System.currentTimeMillis() % availableHosts.size();

        return availableHosts.get((int) index);
    }

}
