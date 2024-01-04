package ru.practicum.admin.categories.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.admin.categories.models.entities.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
}
