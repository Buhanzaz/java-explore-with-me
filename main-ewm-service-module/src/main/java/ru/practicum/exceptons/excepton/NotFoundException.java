package ru.practicum.exceptons.excepton;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
