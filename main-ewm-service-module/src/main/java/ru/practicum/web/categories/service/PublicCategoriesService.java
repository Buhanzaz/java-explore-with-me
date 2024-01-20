package ru.practicum.web.categories.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.models.categories.model.dtos.CategoryDto;

import java.util.List;

public interface PublicCategoriesService {
    List<CategoryDto> getCategories(Pageable pageForCategories);

    CategoryDto getCategoryById(Long catId);
}
