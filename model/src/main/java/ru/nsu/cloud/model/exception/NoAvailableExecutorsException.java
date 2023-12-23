package ru.nsu.cloud.model.exception;

public class NoAvailableExecutorsException extends RuntimeException {

    private static final String MESSAGE = "There are no available executor hosts to process your request";

    public NoAvailableExecutorsException() {
        super(MESSAGE);
    }

}
