package ru.practicum.mappers.comments.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.practicum.models.comments.model.dtos.CommentDto;
import ru.practicum.models.comments.model.entities.CommentEntity;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentMapper {
    CommentEntity toEntity(CommentDto commentDto);

    CommentDto toDto(CommentEntity commentEntity);

    List<CommentDto> toDtoList(List<CommentEntity> commentEntity);

    List<CommentEntity> toEntityList(List<CommentDto> commentEntity);
}