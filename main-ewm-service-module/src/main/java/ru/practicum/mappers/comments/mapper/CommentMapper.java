package ru.practicum.mappers.comments.mapper;

import org.mapstruct.*;
import ru.practicum.models.comments.model.dtos.CommentDto;
import ru.practicum.models.comments.model.entities.CommentEntity;
import ru.practicum.models.events.model.entities.EventEntity;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentMapper {
    CommentEntity toEntity(CommentDto commentDto);

    CommentDto toDto(CommentEntity commentEntity);

    List<CommentDto> toDtoList(List<CommentEntity> commentEntity);
    List<CommentEntity> toEntityList(List<CommentDto> commentEntity);
}