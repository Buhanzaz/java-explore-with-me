package ru.practicum.models.events.model.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Value;
import ru.practicum.models.categories.models.dtos.CategoryDto;
import ru.practicum.enums.State;
import ru.practicum.models.compilations.Locations.model.dtos.LocationDto;
import ru.practicum.models.events.model.entities.EventEntity;
import ru.practicum.models.users.dtos.UserShortDto;

import java.io.Serializable;
import java.time.LocalDateTime;

import static ru.practicum.variables.StaticVariables.FORMATTER;

/**
 * DTO for {@link EventEntity}
 */

@Value
public class EventFullDto implements Serializable {
    Long id;
    String annotation;
    CategoryDto category;
    Long confirmedRequests;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FORMATTER)
    LocalDateTime createdOn;
    String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FORMATTER)
    LocalDateTime eventDate;
    UserShortDto initiator;
    LocationDto location;
    Boolean paid;
    Integer participantLimit;
    String publishedOn;
    Boolean requestModeration;
    State state;
    String title;
    Long views;
}