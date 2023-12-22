package ru.nsu.cloud.core.remote.loadbalance.impl;

import ru.nsu.cloud.core.remote.loadbalance.AbstractBalanceStrategy;
import ru.nsu.cloud.core.remote.loadbalance.exception.NoAvailableHostsException;
import java.util.List;

public class RoundRobinStrategyImpl extends AbstractBalanceStrategy {

    @Override
    public String getNextAddress() {
        List<String> availableHosts = hosts.stream()
            .filter(checkIfHostIsAvailable)
            .toList();

        if (availableHosts.isEmpty()) {
            throw new NoAvailableHostsException();
        }

        return availableHosts.get(CyclicCounter.getNextCounterValue(hosts.size()) % availableHosts.size());
    }

    private static class CyclicCounter {

        private static int countValue = 0;

        private static int getNextCounterValue(int upperBound) {
            return (++countValue == upperBound ? 0 : countValue);
        }

    }

}
