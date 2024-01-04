package ru.practicum.exceptons.excepton;

import org.springframework.dao.DataIntegrityViolationException;

public class ConflictException extends DataIntegrityViolationException {
    private final String reason;

    public ConflictException(String message, String reason) {
        super(message);
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}
