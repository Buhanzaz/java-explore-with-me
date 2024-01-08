package ru.practicum.exceptons.excepton;

public class ChangeStatusRequestException extends RuntimeException {
    public ChangeStatusRequestException(String message) {
        super(message);
    }
}
