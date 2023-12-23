package ru.nsu.cloud.core.remote.loadbalance.exception;

public class NoAvailableHostsException extends RuntimeException {

    private static final String MESSAGE = "There are no available manager hosts to process your request";

    public NoAvailableHostsException() {
        super(MESSAGE);
    }

}
