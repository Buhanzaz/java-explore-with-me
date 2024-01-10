package ru.practicum.exceptons.excepton;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
