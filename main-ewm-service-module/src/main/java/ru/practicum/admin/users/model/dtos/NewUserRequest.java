package ru.practicum.admin.users.model.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;

@Setter
@Getter
@Builder
public class NewUserRequest {
    @Size(min = 6, max = 254)
    @Email
    @NotEmpty
    @NotBlank
    @NotNull
    private final String email;
    @NotEmpty
    @NotBlank
    @NotNull
    private final String name;
}
