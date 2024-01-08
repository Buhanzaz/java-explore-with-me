package ru.practicum.events.mapper;

import org.mapstruct.*;
import ru.practicum.categories.mapper.CategoryMapper;
import ru.practicum.events.model.dtos.*;
import ru.practicum.events.model.entities.EventEntity;
import ru.practicum.users.mapper.UserMapper;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {LocationMapper.class, CategoryMapper.class, UserMapper.class})
public interface EventMapper {
    @Mapping(target = "category", ignore = true)
    EventEntity toEntity(NewEventDto newEventDto);

    EventEntity toEntity(EventFullDto eventFullDto);

    EventFullDto toDto(EventEntity eventEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    EventEntity updateForUser(EventFullDto eventFullDto, @MappingTarget EventEntity eventEntity);

    EventEntity toEntity(EventShortDto eventShortDto);

    EventShortDto toShortDto(EventEntity eventEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    EventEntity updateForUser(EventShortDto eventShortDto, @MappingTarget EventEntity eventEntity);

    @Mapping(source = "category", target = "category.id")
    EventEntity toEntity(UpdateEventUserRequest updateEventUserRequest);

    @Mapping(source = "category.id", target = "category")
    UpdateEventUserRequest toDto1(EventEntity eventEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "category", target = "category.id", ignore = true)
    @Mapping(source = "stateAction", target = "state", ignore = true)
    EventEntity updateForUser(UpdateEventUserRequest updateEventUserRequest, @MappingTarget EventEntity eventEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "category", target = "category.id", ignore = true)
    @Mapping(source = "stateAction", target = "state", ignore = true)
    EventEntity updateForAdmin(UpdateEventAdminRequest updateEventAdminRequest, @MappingTarget EventEntity eventEntity);
//    @InheritInverseConfiguration(name = "toEntity")
//    NewEventDto toDto(EventEntity eventEntity);
//
//    @InheritConfiguration(name = "toEntity")
//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    EventEntity updateForUser(NewEventDto newEventDto, @MappingTarget EventEntity eventEntity);
}