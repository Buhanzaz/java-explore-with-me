package ru.practicum.models.compilations.model.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.models.compilations.model.entities.CompilationEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link CompilationEntity}
 */

@Getter
@Setter
public class NewCompilationDto implements Serializable {
    List<Long> events;
    Boolean pinned = false;
    @Size(min = 1, max = 50)
    @NotBlank
    String title;
}