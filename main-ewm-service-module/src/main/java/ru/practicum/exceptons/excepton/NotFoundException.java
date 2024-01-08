package ru.practicum.exceptons.excepton;

import lombok.Getter;
import org.springframework.dao.DataIntegrityViolationException;

import java.io.IOException;

@Getter
public class NotFoundException extends IOException {
    public NotFoundException(String message) {
        super(message);
    }
}
