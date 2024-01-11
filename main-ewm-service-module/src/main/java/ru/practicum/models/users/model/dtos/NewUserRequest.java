package ru.practicum.models.users.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import ru.practicum.models.users.model.entities.UserEntity;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * DTO for {@link UserEntity}
 */
@Value
@AllArgsConstructor
public class NewUserRequest implements Serializable {
    @Email
    @NotEmpty
    @NotBlank
    @NotNull
    @Size(min = 6, max = 254)
    String email;
    @NotEmpty
    @NotBlank
    @NotNull
    @Size(min = 2, max = 250)
    String name;
}
