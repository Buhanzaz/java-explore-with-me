package ru.practicum.users.mapper;

import org.mapstruct.*;
import ru.practicum.users.model.dtos.ParticipationRequestDto;
import ru.practicum.users.model.entities.ParticipationRequestEntity;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface RequestMapper {
    @Mapping(target = "requester", ignore = true)
    @Mapping(target = "event", ignore = true)
    ParticipationRequestEntity toEntity(ParticipationRequestDto participationRequestDto);

    @InheritInverseConfiguration(name = "toEntity")
    @Mapping(source = "requester.id", target = "requester")
    @Mapping(source = "event.id", target = "event")
    ParticipationRequestDto toDto(ParticipationRequestEntity participationRequestEntity);

    List<ParticipationRequestDto> toDtoList(List<ParticipationRequestEntity> entityList);
}