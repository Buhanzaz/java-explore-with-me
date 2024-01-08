package ru.practicum.users.model.dtos;

import lombok.*;
import ru.practicum.users.model.entities.UserEntity;

import java.io.Serializable;

/**
 * DTO for {@link UserEntity}
 */
@Value
@Builder
public class UserShortDto implements Serializable {
    Long id;
    String name;
}