package ru.practicum.web.requests.service;

import ru.practicum.models.requests.model.dtos.ParticipationRequestDto;

import java.util.List;

public interface PrivateUserRequestService {
    List<ParticipationRequestDto> getParticipationRequest(Long userId);

    ParticipationRequestDto participationRequest(Long userId, Long eventId);

    ParticipationRequestDto cancelParticipationRequest(Long userId, Long requestId);
}
