package ru.practicum.mapper;

import org.mapstruct.Mapper;
import ru.practicum.dto.StatisticDto;
import ru.practicum.model.StatisticEntity;

@Mapper(componentModel = "spring")
public interface StatsServerMapper {
    StatisticDto toDto(StatisticEntity entity);

    StatisticEntity toEntity(StatisticDto dto);
}