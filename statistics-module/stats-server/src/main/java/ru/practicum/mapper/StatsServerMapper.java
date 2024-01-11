package ru.practicum.mapper;

import org.mapstruct.Mapper;
import ru.practicum.dto.EndpointHit;
import ru.practicum.model.EndpointHitEntity;

@Mapper(componentModel = "spring")
public interface StatsServerMapper {
    EndpointHit toDto(EndpointHitEntity entity);

    EndpointHitEntity toEntity(EndpointHit dto);
}