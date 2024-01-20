package ru.practicum.models.events.model.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.enums.State;
import ru.practicum.models.Locations.model.dtos.LocationDto;
import ru.practicum.models.categories.model.dtos.CategoryDto;
import ru.practicum.models.comments.model.dtos.CommentDto;
import ru.practicum.models.events.model.entities.EventEntity;
import ru.practicum.models.users.model.dtos.UserShortDto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.utils.variables.StaticVariables.FORMATTER;

/**
 * DTO for {@link EventEntity}
 */

@Data
public class ReviewEventFullDto implements Serializable {
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
    List<CommentDto> comments;
}