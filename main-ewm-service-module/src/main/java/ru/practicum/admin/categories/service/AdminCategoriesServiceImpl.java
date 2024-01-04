package ru.practicum.admin.categories.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.admin.categories.mapper.CategoryMapper;
import ru.practicum.admin.categories.models.dtos.CategoryDto;
import ru.practicum.admin.categories.models.dtos.NewCategoryDto;
import ru.practicum.admin.categories.models.entities.CategoryEntity;
import ru.practicum.admin.categories.repository.CategoryRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminCategoriesServiceImpl implements AdminCategoriesService {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    @Override
    public CategoryDto addCategory(NewCategoryDto newCategoryDto) {
        CategoryEntity entity = mapper.toEntity(newCategoryDto);
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
