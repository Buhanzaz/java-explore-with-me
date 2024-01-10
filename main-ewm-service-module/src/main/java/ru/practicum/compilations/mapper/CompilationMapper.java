package ru.practicum.compilations.mapper;

import org.mapstruct.*;
import ru.practicum.models.compilations.model.dtos.CompilationDto;
import ru.practicum.models.compilations.model.dtos.UpdateCompilationRequest;
import ru.practicum.models.compilations.model.entities.CompilationEntity;
import ru.practicum.models.compilations.model.dtos.NewCompilationDto;
import ru.practicum.events.mapper.EventMapper;
import ru.practicum.models.events.model.entities.EventEntity;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {EventMapper.class})
public interface CompilationMapper {
    @Mapping(target = "events", source = "events")
    CompilationEntity toEntityNotNullEvents(NewCompilationDto compilationDto, List<EventEntity> events);

    @Mapping(target = "events", ignore = true)
    CompilationEntity toEntity(NewCompilationDto compilationDto);

    CompilationDto toDto(CompilationEntity compilationEntity);

    List<CompilationDto> toDtoList(List<CompilationEntity> compilationEntityList);

    @Mapping(target = "events", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CompilationEntity partialUpdate(UpdateCompilationRequest updateCompilation, @MappingTarget CompilationEntity compilationEntity);
}