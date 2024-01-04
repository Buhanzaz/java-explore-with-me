package ru.practicum.admin.categories.service;

import ru.practicum.admin.categories.models.dtos.CategoryDto;
import ru.practicum.admin.categories.models.dtos.NewCategoryDto;

public interface AdminCategoriesService {
    CategoryDto addCategory(NewCategoryDto newCategoryDto);

    void deleteCategory(Long catId);

    CategoryDto updateCategory(Long catId, CategoryDto categoryDto);
}
