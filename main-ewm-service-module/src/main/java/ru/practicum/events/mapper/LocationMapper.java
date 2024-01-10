package ru.practicum.events.mapper;

import org.mapstruct.*;
import ru.practicum.models.compilations.Locations.model.dtos.LocationDto;
import ru.practicum.models.compilations.Locations.model.entities.LocationEntity;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface LocationMapper {
    LocationEntity toEntity(LocationDto locationDto);

    LocationDto toDto(LocationEntity locationEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(LocationDto locationDto, @MappingTarget LocationEntity locationEntity);
}