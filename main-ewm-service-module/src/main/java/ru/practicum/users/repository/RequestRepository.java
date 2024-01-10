package ru.practicum.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.users.model.entities.ParticipationRequestEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<ParticipationRequestEntity, Long> {

    List<ParticipationRequestEntity> findAllByRequester_Id(Long userId);

    Optional<ParticipationRequestEntity> findFirstByRequester_IdAndId(Long userId, Long requestId);

    List<ParticipationRequestEntity> findAllByEvent_Initiator_IdAndEvent_Id(Long userId, Long eventId);
}
