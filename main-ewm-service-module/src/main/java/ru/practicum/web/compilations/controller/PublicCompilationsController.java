package ru.practicum.web.compilations.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.models.compilations.model.dtos.CompilationDto;
import ru.practicum.web.compilations.service.PublicCompilationsService;
import ru.practicum.utils.Pages;

import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/compilations")
public class PublicCompilationsController {
    private final PublicCompilationsService service;

    @GetMapping
    public List<CompilationDto> getCompilations(@RequestParam(required = false) Boolean pinned,
                                                @RequestParam(defaultValue = "0", required = false) @PositiveOrZero Integer from,
                                                @RequestParam(defaultValue = "10", required = false) @Min(1) Integer size) {
        Pageable pageForCompilations = Pages.getPageForCompilations(from, size);

        return service.getCompilations(pinned, pageForCompilations);
    }

    @GetMapping("/{compId}")
    public CompilationDto getCompilationById(@PathVariable Long compId) {
        return service.getCompilationById(compId);
    }
}
