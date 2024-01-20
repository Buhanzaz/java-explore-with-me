package ru.practicum.web.compilations.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.models.compilations.model.entities.CompilationEntity;

import java.util.List;

public interface CompilationRepository extends JpaRepository<CompilationEntity, Long> {
    List<CompilationEntity> findAllByPinned(Boolean pinned, Pageable pageable);
}