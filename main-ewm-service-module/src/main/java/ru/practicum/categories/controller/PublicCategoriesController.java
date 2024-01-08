package ru.practicum.categories.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.categories.models.dtos.CategoryDto;
import ru.practicum.categories.service.PublicCategoriesService;
import ru.practicum.utils.Pages;

import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Validated
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class PublicCategoriesController {
    private final PublicCategoriesService service;

    @GetMapping
    public List<CategoryDto> getCategories(@RequestParam(defaultValue = "0", required = false) @PositiveOrZero Integer from,
                                           @RequestParam(defaultValue = "10", required = false) @Min(1) Integer size) {
        Pageable pageForCategories = Pages.getPageForCategories(from, size);
        return service.getCategories(pageForCategories);
    }

    @GetMapping("/{catId}")
    public CategoryDto getCategoryById(@PathVariable Long catId) {
        return service.getCategoryById(catId);
    }
}
