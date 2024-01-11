package ru.practicum.models.users.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import ru.practicum.models.users.model.entities.UserEntity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * DTO for {@link UserEntity}
 */
@Value
@AllArgsConstructor
public class UserDto implements Serializable {
    Long id;
    @Size(min = 6, max = 254)
    @Email
    @NotEmpty
    @NotBlank
    String email;
    @NotEmpty
    @NotBlank
    String name;
}