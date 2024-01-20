package ru.practicum.web.compilations.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.models.compilations.model.dtos.CompilationDto;
import ru.practicum.models.compilations.model.dtos.NewCompilationDto;
import ru.practicum.models.compilations.model.dtos.UpdateCompilationRequest;
import ru.practicum.web.compilations.service.AdminCompilationsService;

import javax.validation.Valid;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/compilations")
public class AdminCompilationsController {
    private final AdminCompilationsService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto addCompilations(@RequestBody @Valid NewCompilationDto newCompilationDto) {
        return service.addCompilations(newCompilationDto);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilations(@PathVariable Long compId) {
        service.deleteCompilations(compId);
    }

    @PatchMapping("/{compId}")
    public CompilationDto patchCompilation(@PathVariable Long compId,
                                           @RequestBody @Valid UpdateCompilationRequest updateCompilation) {
        return service.patchCompilation(compId, updateCompilation);
    }
}
