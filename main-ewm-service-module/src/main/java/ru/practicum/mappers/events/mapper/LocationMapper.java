package ru.practicum.mappers.events.mapper;

import org.mapstruct.*;
import ru.practicum.models.Locations.model.dtos.LocationDto;
import ru.practicum.models.Locations.model.entities.LocationEntity;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface LocationMapper {
    LocationEntity toEntity(LocationDto locationDto);

    LocationDto toDto(LocationEntity locationEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(LocationDto locationDto, @MappingTarget LocationEntity locationEntity);
}