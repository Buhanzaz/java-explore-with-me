package ru.practicum.web.categories.service;

import ru.practicum.models.categories.model.dtos.CategoryDto;
import ru.practicum.models.categories.model.dtos.NewCategoryDto;

public interface AdminCategoriesService {
    CategoryDto addCategory(NewCategoryDto newCategoryDto);

    void deleteCategory(Long catId);

    CategoryDto updateCategory(Long catId, CategoryDto categoryDto);
}
