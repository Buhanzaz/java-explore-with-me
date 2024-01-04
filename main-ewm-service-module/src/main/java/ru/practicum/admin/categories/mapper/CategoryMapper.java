package ru.practicum.admin.categories.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.practicum.admin.categories.models.dtos.CategoryDto;
import ru.practicum.admin.categories.models.dtos.NewCategoryDto;
import ru.practicum.admin.categories.models.entities.CategoryEntity;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryEntity toEntity(NewCategoryDto dto);

    CategoryDto toDto(CategoryEntity entity);

    @Mapping(target = "id", ignore = true)
    void updateCategoryEntity(@MappingTarget CategoryEntity entity, CategoryDto dto);
}
