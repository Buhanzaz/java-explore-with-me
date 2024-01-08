package ru.practicum.users.model.dtos;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;
import java.io.Serializable;

@Value
@Builder
public class NewUserRequest implements Serializable {
    @Size(min = 6, max = 254)
    @Email
    @NotEmpty
    @NotBlank
    @NotNull
    String email;
    @NotEmpty
    @NotBlank
    @NotNull
    String name;
}
