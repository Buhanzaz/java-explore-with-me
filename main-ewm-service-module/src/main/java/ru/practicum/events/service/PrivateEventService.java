package ru.practicum.events.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.events.model.dtos.*;
import ru.practicum.users.model.dtos.EventRequestStatusUpdateRequest;
import ru.practicum.users.model.dtos.EventRequestStatusUpdateResult;
import ru.practicum.users.model.dtos.ParticipationRequestDto;

import java.util.List;

public interface PrivateEventService {
    EventFullDto addEvent(Long userId, NewEventDto newEventDto);

    List<EventShortDto> getEventsForOwner(Long userId, Pageable pageable);

    EventFullDto getEventForOwner(Long userId, Long eventId);

    EventFullDto ownerUpdateEvent(Long userId, Long eventId, UpdateEventUserRequest updateEventsDto);

    List<ParticipationRequestDto> getUserRequests(Long userId, Long eventId);

    EventRequestStatusUpdateResult changingStatusRequest(Long userId, Long eventId, EventRequestStatusUpdateRequest updateRequest);
}
