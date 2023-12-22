package ru.nsu.cloud.core.remote.loadbalance;

import lombok.Setter;

import java.util.List;

@Setter
public abstract class AbstractBalanceStrategy implements BalanceStrategy {

    public abstract BalanceStrategy instantiate(List<String> hosts);

}
