package ru.nsu.cloud.model.exception;

public class NoAvailableManagersException extends RuntimeException {

    private static final String MESSAGE = "There are no available manager hosts to process your request";

    public NoAvailableManagersException() {
        super(MESSAGE);
    }

}
