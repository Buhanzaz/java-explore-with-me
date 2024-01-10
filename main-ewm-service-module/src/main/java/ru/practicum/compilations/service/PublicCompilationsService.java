package ru.practicum.compilations.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.compilations.model.dtos.CompilationDto;

import java.util.List;

public interface PublicCompilationsService {
    CompilationDto getCompilationById(Long compId);

    List<CompilationDto> getCompilations(Boolean pinned, Pageable pageForCompilations);
}
