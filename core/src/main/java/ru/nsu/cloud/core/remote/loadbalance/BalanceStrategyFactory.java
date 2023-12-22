package ru.nsu.cloud.core.remote.loadbalance;

import ru.nsu.cloud.core.remote.loadbalance.impl.RoundRobinStrategyImpl;

import java.util.Map;

public class BalanceStrategyFactory {

    private static final Map<String, Class<? extends AbstractBalanceStrategy>> nameToImpl = Map.of(
        "roundrobin", RoundRobinStrategyImpl.class
    );

    public AbstractBalanceStrategy getStrategy(String strategy) throws InstantiationException, IllegalAccessException {
        return nameToImpl.get(strategy).newInstance();
    }

}
