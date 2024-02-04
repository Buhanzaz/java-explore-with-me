package ru.practicum.web.events.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.enums.State;
import ru.practicum.models.events.model.entities.EventEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {
    Optional<EventEntity> findByIdAndState(Long eventId, State state);

    Optional<EventEntity> findFirstByInitiator_IdAndId(Long userId, Long eventId);

    List<EventEntity> findAllByInitiator_Id(Long userId, Pageable pageable);

    List<EventEntity> findAllByState(State state);
}
