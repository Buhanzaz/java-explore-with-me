package ru.practicum.compilations.service;

import ru.practicum.compilations.model.dtos.CompilationDto;
import ru.practicum.compilations.model.dtos.NewCompilationDto;
import ru.practicum.compilations.model.dtos.UpdateCompilationRequest;

public interface AdminCompilationsService {
    CompilationDto addCompilations(NewCompilationDto newCompilationDto);

    void deleteCompilations(Long compId);

    CompilationDto patchCompilation(Long compId, UpdateCompilationRequest updateCompilation);
}
