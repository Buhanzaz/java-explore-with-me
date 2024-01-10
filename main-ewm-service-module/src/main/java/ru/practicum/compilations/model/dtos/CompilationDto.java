package ru.practicum.compilations.model.dtos;

import lombok.Value;
import ru.practicum.events.model.dtos.EventShortDto;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link ru.practicum.compilations.model.entities.CompilationEntity}
 */

@Value
public class CompilationDto implements Serializable {
    Long id;
    List<EventShortDto> events;
    Boolean pinned;
    String title;
}