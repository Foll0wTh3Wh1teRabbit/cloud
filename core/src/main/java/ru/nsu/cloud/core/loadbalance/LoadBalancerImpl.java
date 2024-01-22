package ru.nsu.cloud.core.loadbalance;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.nsu.cloud.core.loadbalance.impl.BalanceStrategy;
import ru.nsu.cloud.core.loadbalance.impl.RoundRobinStrategyImpl;
import ru.nsu.cloud.core.loadbalance.impl.TimestampStrategyImpl;
import ru.nsu.cloud.core.loadbalance.util.PreflightHealthChecker;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoadBalancerImpl implements LoadBalancer {

    private final PreflightHealthChecker preflightHealthChecker;

    @Value("${nsucloud.hosts}")
    private List<String> hosts;

    @Value("${nsucloud.balancer.strategy}")
    private String balanceStrategyLiteral;

    private BalanceStrategy balanceStrategy;

    @PostConstruct
    public void init() {
        Map<String, Supplier<BalanceStrategy>> balanceStrategyFactory = Map.of(
            "roundrobin", () -> new RoundRobinStrategyImpl(preflightHealthChecker, hosts),
            "timestamp", () -> new TimestampStrategyImpl(preflightHealthChecker, hosts)
        );

        balanceStrategy = balanceStrategyFactory.get(balanceStrategyLiteral).get();
    }

    @Override
    public String getAvailableHost() {
        return balanceStrategy.getNextAddress();
    }

}
