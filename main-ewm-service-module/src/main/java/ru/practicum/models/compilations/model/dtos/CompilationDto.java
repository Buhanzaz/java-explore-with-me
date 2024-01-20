package ru.practicum.models.compilations.model.dtos;

import lombok.Value;
import ru.practicum.models.compilations.model.entities.CompilationEntity;
import ru.practicum.models.events.model.dtos.EventShortDto;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link CompilationEntity}
 */

@Value
public class CompilationDto implements Serializable {
    Long id;
    List<EventShortDto> events;
    Boolean pinned;
    String title;
}