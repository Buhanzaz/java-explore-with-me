package ru.practicum.web.events.service;

import ru.practicum.enums.State;
import ru.practicum.models.events.model.dtos.EventFullDto;
import ru.practicum.models.events.model.dtos.UpdateEventAdminRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminEventService {
    List<EventFullDto> searchEventForAdmin(List<Long> users, List<State> states, List<Long> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size);

    EventFullDto reviewEvent(Long eventId, UpdateEventAdminRequest updateEvent);
}
