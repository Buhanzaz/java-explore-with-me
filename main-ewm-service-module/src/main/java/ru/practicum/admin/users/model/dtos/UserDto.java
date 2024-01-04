package ru.practicum.admin.users.model.dtos;

import lombok.Builder;
import lombok.Data;
import ru.practicum.admin.users.model.entities.UserEntity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * DTO for {@link UserEntity}
 */
@Data
@Builder
public class UserDto {
    private final Long id;
    @Size(min = 6, max = 254)
    @Email
    @NotEmpty
    @NotBlank
    private final String email;
    @NotEmpty
    @NotBlank
    private final String name;
}