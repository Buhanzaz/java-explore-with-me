package ru.practicum.web.categories.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mappers.categories.mapper.CategoryMapper;
import ru.practicum.web.categories.service.AdminCategoriesService;
import ru.practicum.models.categories.model.dtos.CategoryDto;
import ru.practicum.models.categories.model.dtos.NewCategoryDto;
import ru.practicum.models.categories.model.entities.CategoryEntity;
import ru.practicum.web.categories.repository.CategoryRepository;

@Service
@Transactional
@RequiredArgsConstructor
class AdminCategoriesServiceImpl implements AdminCategoriesService {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    @Override
    public CategoryDto addCategory(NewCategoryDto newCategoryDto) {
        CategoryEntity entity = mapper.toNewEntity(newCategoryDto);
        CategoryEntity save = repository.save(entity);

        return mapper.toDto(save);
    }

    @Override
    public void deleteCategory(Long catId) {
        repository.deleteById(catId);
    }

    //TODO нужно в этом месте внимательно настроить транзакции
    @Override
    public CategoryDto updateCategory(Long catId, CategoryDto categoryDto) {
        //TODO Нужно в orElseThrow добавить нормальную генерацию ошибки
        CategoryEntity categoryEntity = repository.findById(catId).orElseThrow();
        mapper.updateCategoryEntity(categoryEntity, categoryDto);
        return mapper.toDto(categoryEntity);
    }
}
