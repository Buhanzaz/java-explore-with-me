package ru.practicum.models.events.model.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.enums.StateActionForAdmin;
import ru.practicum.models.compilations.Locations.model.dtos.LocationDto;
import ru.practicum.models.events.model.entities.EventEntity;

import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

import static ru.practicum.variables.StaticVariables.FORMATTER;

/**
 * DTO for {@link EventEntity}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventAdminRequest implements Serializable {
    @Size(min = 20, max = 2000)
    private String annotation;
    private Long category;
    @Size(min = 20, max = 7000)
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FORMATTER)
    private LocalDateTime eventDate;
    private LocationDto location;
    private Boolean paid;
    @PositiveOrZero
    private Integer participantLimit;
    private Boolean requestModeration;
    private StateActionForAdmin stateAction;
    @Size(min = 3, max = 120)
    private String title;
}