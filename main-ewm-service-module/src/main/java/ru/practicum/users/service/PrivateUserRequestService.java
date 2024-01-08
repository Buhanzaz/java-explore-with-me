package ru.practicum.users.service;

import ru.practicum.users.model.dtos.ParticipationRequestDto;

import java.util.List;

public interface PrivateUserRequestService {
    List<ParticipationRequestDto> getParticipationRequest(Long userId);

    ParticipationRequestDto participationRequest(Long userId, Long eventId);

    ParticipationRequestDto cancelParticipationRequest(Long userId, Long requestId);
}
