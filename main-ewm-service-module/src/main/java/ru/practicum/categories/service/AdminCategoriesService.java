package ru.practicum.categories.service;

import ru.practicum.categories.models.dtos.CategoryDto;
import ru.practicum.categories.models.dtos.NewCategoryDto;

public interface AdminCategoriesService {
    CategoryDto addCategory(NewCategoryDto newCategoryDto);

    void deleteCategory(Long catId);

    CategoryDto updateCategory(Long catId, CategoryDto categoryDto);
}