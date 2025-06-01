package ru.practicum.exception.handler;

public class IncorrectDataException extends RuntimeException {
    public IncorrectDataException(String message) {
        super(message);
    }
}
