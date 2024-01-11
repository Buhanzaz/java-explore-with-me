package ru.practicum.web.compilations.service;

import ru.practicum.models.compilations.model.dtos.CompilationDto;
import ru.practicum.models.compilations.model.dtos.NewCompilationDto;
import ru.practicum.models.compilations.model.dtos.UpdateCompilationRequest;

public interface AdminCompilationsService {
    CompilationDto addCompilations(NewCompilationDto newCompilationDto);

    void deleteCompilations(Long compId);

    CompilationDto patchCompilation(Long compId, UpdateCompilationRequest updateCompilation);
}
