package ru.practicum.compilations.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.compilations.mapper.CompilationMapper;
import ru.practicum.compilations.model.dtos.CompilationDto;
import ru.practicum.compilations.model.dtos.NewCompilationDto;
import ru.practicum.compilations.model.dtos.UpdateCompilationRequest;
import ru.practicum.compilations.model.entities.CompilationEntity;
import ru.practicum.compilations.repository.CompilationRepository;
import ru.practicum.compilations.service.AdminCompilationsService;
import ru.practicum.events.model.entities.EventEntity;
import ru.practicum.events.repository.EventRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminCompilationsServiceImpl implements AdminCompilationsService {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;
    private final CompilationMapper compilationMapper;

    @Override
    public CompilationDto addCompilations(NewCompilationDto newCompilationDto) {
        CompilationEntity entity;
        List<EventEntity> events;
        if (newCompilationDto.getEvents() != null && !newCompilationDto.getEvents().isEmpty()) {
            events = eventRepository.findAllById(newCompilationDto.getEvents());
        } else {
            events = new ArrayList<>();
        }

        entity = compilationMapper.toEntityNotNullEvents(newCompilationDto, events);

        return compilationMapper.toDto(compilationRepository.save(entity));
    }

    @Override
    public void deleteCompilations(Long compId) {
        compilationRepository.deleteById(compId);
    }

    @Override
    public CompilationDto patchCompilation(Long compId, UpdateCompilationRequest updateCompilation) {
        CompilationEntity entity = compilationRepository.findById(compId).orElseThrow();
        CompilationEntity compilationUpdated = compilationMapper.partialUpdate(updateCompilation, entity);
        if (updateCompilation.getEvents() != null) {
            List<EventEntity> allById = eventRepository.findAllById(updateCompilation.getEvents());
            compilationUpdated.setEvents(allById);
        }
        return compilationMapper.toDto(compilationUpdated);
    }
}
