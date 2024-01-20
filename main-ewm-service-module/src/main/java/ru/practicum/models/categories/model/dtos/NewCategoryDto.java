package ru.practicum.models.categories.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * DTO for {@link ru.practicum.models.categories.model.entities.CategoryEntity}
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewCategoryDto {
    @NotNull
    @NotEmpty
    @NotBlank
    @Length(min = 1, max = 50)
    String name;
}
