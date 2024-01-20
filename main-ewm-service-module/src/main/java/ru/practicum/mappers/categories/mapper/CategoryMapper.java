package ru.practicum.mappers.categories.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.practicum.models.categories.model.dtos.CategoryDto;
import ru.practicum.models.categories.model.dtos.NewCategoryDto;
import ru.practicum.models.categories.model.entities.CategoryEntity;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(target = "id", ignore = true)
    CategoryEntity toNewEntity(NewCategoryDto dto);

    CategoryEntity toEntity(CategoryDto dto);

    CategoryDto toDto(CategoryEntity entity);

    @Mapping(target = "id", ignore = true)
    void updateCategoryEntity(@MappingTarget CategoryEntity entity, CategoryDto dto);
}
