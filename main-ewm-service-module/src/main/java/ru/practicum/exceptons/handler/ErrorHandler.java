package ru.practicum.exceptons.handler;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.exceptons.ApiError;
import ru.practicum.exceptons.excepton.*;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler({MethodArgumentNotValidException.class, MissingServletRequestParameterException.class,
            DataAndTimeException.class, EnumException.class, ClientStatisticsException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handlerBadRequest(final Exception exception) {
        log.debug("Получен статус 404 Bad request {}", exception.getMessage(), exception);
        return ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .reason("Incorrectly made request.")
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now()).build();
    }

    @ExceptionHandler({DataIntegrityViolationException.class, ConstraintViolationException.class,
            ConflictException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handlerConflict(final Exception exception) {
        log.debug("Получен статус 500 Internal server error {}", exception.getMessage(), exception);
        return ApiError.builder()
                .status(HttpStatus.CONFLICT)
                .reason("Integrity constraint has been violated.")
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now()).build();
    }

    @ExceptionHandler({EmptyResultDataAccessException.class, IllegalArgumentException.class,
            NoSuchElementException.class, NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handlerNotFound(final Exception exception) {
        log.debug("Получен статус 500 Internal server error {}", exception.getMessage(), exception);
        return ApiError.builder()
                .status(HttpStatus.NOT_FOUND)
                .reason("The required object was not found.")
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now()).build();
    }
}
