package ru.practicum.users.mapper;

import org.mapstruct.*;
import ru.practicum.users.model.dtos.ParticipationRequestDto;
import ru.practicum.users.model.entities.ParticipationRequestEntity;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface RequestMapper {
    @Mapping(target = "requester", ignore = true)
    @Mapping(target = "event", ignore = true)
    ParticipationRequestEntity toEntity(ParticipationRequestDto participationRequestDto);

    @InheritInverseConfiguration(name = "toEntity")
    @Mapping(source = "requester.id", target = "requester")
    @Mapping(source = "event.id", target = "event")
    ParticipationRequestDto toDto(ParticipationRequestEntity participationRequestEntity);

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ParticipationRequestEntity partialUpdate(ParticipationRequestDto participationRequestDto, @MappingTarget ParticipationRequestEntity participationRequestEntity);
}