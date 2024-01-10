package ru.practicum.compilations.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.compilations.mapper.CompilationMapper;
import ru.practicum.models.compilations.model.dtos.CompilationDto;
import ru.practicum.models.compilations.model.entities.CompilationEntity;
import ru.practicum.compilations.repository.CompilationRepository;
import ru.practicum.compilations.service.PublicCompilationsService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicCompilationsServiceImpl implements PublicCompilationsService {
    private final CompilationRepository compilationRepository;
    private final CompilationMapper compilationMapper;

    @Override
    public CompilationDto getCompilationById(Long compId) {
        CompilationEntity entity = compilationRepository.findById(compId).orElseThrow();

        return compilationMapper.toDto(entity);
    }

    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, Pageable pageForCompilations) {
        List<CompilationEntity> entityList;
        if (pinned != null) {
            entityList = compilationRepository.findAllByPinned(pinned, pageForCompilations);
        } else {
            entityList = compilationRepository.findAll(pageForCompilations).toList();
        }

        return compilationMapper.toDtoList(entityList);
    }
}
