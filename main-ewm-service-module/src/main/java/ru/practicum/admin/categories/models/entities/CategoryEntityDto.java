package ru.practicum.admin.categories.models.entities;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * DTO for {@link CategoryEntity}
 */
@Value
public class CategoryEntityDto implements Serializable {
    Long id;
    @NotNull
    @NotEmpty
    @NotBlank
    String name;
}