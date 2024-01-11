package ru.practicum.models.Locations.model.dtos;

import lombok.Value;
import ru.practicum.models.Locations.model.entities.LocationEntity;

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