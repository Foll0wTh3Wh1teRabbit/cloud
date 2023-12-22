package ru.nsu.cloud.core.remote.loadbalance;

public interface BalanceStrategy {

    String getNextHost();

}
