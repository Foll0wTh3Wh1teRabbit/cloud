package ru.nsu.cloud.core.loadbalance.util;

public interface PreflightHealthChecker {

    boolean sendPreflightRequest(String host);

}
