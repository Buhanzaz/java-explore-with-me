package ru.practicum.events.model.dtos;

import lombok.Value;
import ru.practicum.events.model.entities.LocationEntity;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * DTO for {@link LocationEntity}
 */
@Value
public class LocationDto implements Serializable {
    @NotNull
    Float lat;
    @NotNull
    Float lon;
}