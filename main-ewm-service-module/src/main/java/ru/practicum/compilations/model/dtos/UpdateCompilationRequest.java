package ru.practicum.compilations.model.dtos;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link ru.practicum.compilations.model.entities.CompilationEntity}
 */

@lombok.Value
public class UpdateCompilationRequest implements Serializable {
    List<Long> events;
    Boolean pinned;
    @Size(max = 50)
    String title;
}
