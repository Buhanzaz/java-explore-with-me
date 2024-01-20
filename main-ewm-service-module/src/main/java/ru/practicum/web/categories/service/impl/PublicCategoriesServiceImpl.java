package ru.practicum.web.categories.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exceptons.excepton.NotFoundException;
import ru.practicum.mappers.categories.mapper.CategoryMapper;
import ru.practicum.web.categories.service.PublicCategoriesService;
import ru.practicum.models.categories.model.dtos.CategoryDto;
import ru.practicum.models.categories.model.entities.CategoryEntity;
import ru.practicum.web.categories.repository.CategoryRepository;

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

    @Override
    public CategoryDto getCategoryById(Long catId) {
        CategoryEntity entity = repository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Not found category"));

        return mapper.toDto(entity);
    }
}
