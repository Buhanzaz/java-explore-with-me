package ru.practicum.models.events.model.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.practicum.models.Locations.model.dtos.LocationDto;
import ru.practicum.models.events.model.entities.EventEntity;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;

import static ru.practicum.utils.variables.StaticVariables.FORMATTER;

/**
 * DTO for {@link EventEntity}
 */
@Data
public class NewEventDto implements Serializable {
    @NotNull
    @Size(min = 20, max = 2000)
    @NotEmpty
    @NotBlank
    private String annotation;
    private Long category;
    @NotNull
    @Size(min = 20, max = 7000)
    @NotEmpty
    @NotBlank
    private String description;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FORMATTER)
    private LocalDateTime eventDate;
    private LocationDto location;
    private Boolean paid;
    @PositiveOrZero
    private Integer participantLimit;
    private Boolean requestModeration;
    @NotNull
    @Size(min = 3, max = 120)
    @NotEmpty
    @NotBlank
    private String title;
}