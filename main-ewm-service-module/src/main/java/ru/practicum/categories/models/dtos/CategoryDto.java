package ru.practicum.categories.models.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Value
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryDto implements Serializable {
    Long id;

    @NotNull
    @NotEmpty
    @NotBlank
    @Length(min = 1, max = 50)
    String name;
}
