package ru.practicum.models.compilations.model.dtos;

import lombok.Value;
import ru.practicum.models.compilations.model.entities.CompilationEntity;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link CompilationEntity}
 */

@Value
public class UpdateCompilationRequest implements Serializable {
    List<Long> events;
    Boolean pinned;
    @Size(max = 50)
    String title;
}
