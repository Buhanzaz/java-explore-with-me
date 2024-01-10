package ru.practicum.compilations.model.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link ru.practicum.compilations.model.entities.CompilationEntity}
 */

@Data
public class NewCompilationDto implements Serializable {
    List<Long> events;
    Boolean pinned = false;
    @Size(min = 1, max = 50)
    @NotBlank
    String title;
}