package ru.practicum.web.compilations.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.models.compilations.model.dtos.CompilationDto;

import java.util.List;

public interface PublicCompilationsService {
    CompilationDto getCompilationById(Long compId);

    List<CompilationDto> getCompilations(Boolean pinned, Pageable pageForCompilations);
}
