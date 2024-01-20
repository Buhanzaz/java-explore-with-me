package ru.practicum.mappers.events.mapper;

import org.mapstruct.*;
import ru.practicum.mappers.categories.mapper.CategoryMapper;
import ru.practicum.models.events.model.dtos.*;
import ru.practicum.models.events.model.entities.EventEntity;
import ru.practicum.mappers.users.mapper.UserMapper;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {LocationMapper.class, CategoryMapper.class, UserMapper.class})
public interface EventMapper {

    EventFullDto toFullDto(EventEntity eventEntity);

    ReviewEventFullDto toReviewDto(EventEntity eventEntity);

    EventShortDto toShortDto(EventEntity eventEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "category", target = "category.id", ignore = true)
    @Mapping(source = "stateAction", target = "state", ignore = true)
    EventEntity updateForUser(UpdateEventUserRequest updateEventUserRequest, @MappingTarget EventEntity eventEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "category", target = "category.id", ignore = true)
    @Mapping(source = "stateAction", target = "state", ignore = true)
    EventEntity updateForAdmin(UpdateEventAdminRequest updateEventAdminRequest, @MappingTarget EventEntity eventEntity);

    List<EventFullDto> toDtoList(List<EventEntity> entities);
}