package ru.practicum.events.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.events.model.dtos.EventFullDto;
import ru.practicum.events.model.entities.EventEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {

    Optional<EventEntity> findFirstByInitiator_IdAndId(Long userId, Long eventId);

    List<EventEntity> findAllByInitiator_Id(Long userId, Pageable pageable);
}
