package ru.nsu.cloud.core.loadbalance.impl;

public interface BalanceStrategy {

    String getNextAddress();

}
