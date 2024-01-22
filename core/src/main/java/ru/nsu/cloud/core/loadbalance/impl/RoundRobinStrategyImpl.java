package ru.nsu.cloud.core.loadbalance.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.nsu.cloud.core.loadbalance.util.PreflightHealthChecker;
import ru.nsu.cloud.model.exception.NoAvailableManagersException;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class RoundRobinStrategyImpl implements BalanceStrategy {

    private final PreflightHealthChecker preflightHealthChecker;

    private final List<String> hosts;

    @Override
    public String getNextAddress() {
        List<String> availableHosts = hosts.stream()
            .filter(preflightHealthChecker::sendPreflightRequest)
            .toList();

        if (availableHosts.isEmpty()) {
            throw new NoAvailableManagersException();
        }

        return availableHosts.get(CyclicCounter.getNextCounterValue(hosts.size()) % availableHosts.size());
    }

    private static class CyclicCounter {

        private static int countValue = -1;

        private static int getNextCounterValue(int upperBound) {
            return (++countValue == upperBound ? 0 : countValue);
        }

    }

}
