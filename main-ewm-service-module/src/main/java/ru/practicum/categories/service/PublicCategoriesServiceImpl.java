package ru.practicum.categories.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.categories.mapper.CategoryMapper;
import ru.practicum.categories.models.dtos.CategoryDto;
import ru.practicum.categories.models.entities.CategoryEntity;
import ru.practicum.categories.repository.CategoryRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
class PublicCategoriesServiceImpl implements PublicCategoriesService {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    @Override
    public List<CategoryDto> getCategories(Pageable pageForCategories) {
        Page<CategoryEntity> allCategories = repository.findAll(pageForCategories);

        return allCategories.hasContent() ? allCategories.getContent()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList()) : Collections.emptyList();
    }

    //TODO Решить проблему с orElseThrow
    @Override
    public CategoryDto getCategoryById(Long catId) {
        CategoryEntity entity = repository.findById(catId).orElseThrow();

        return mapper.toDto(entity);
    }
}
