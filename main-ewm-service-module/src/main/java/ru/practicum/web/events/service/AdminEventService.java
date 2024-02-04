package ru.practicum.web.events.service;

import ru.practicum.enums.State;
import ru.practicum.models.events.model.dtos.EventFullDto;
import ru.practicum.models.events.model.dtos.ReviewEventFullDto;
import ru.practicum.models.events.model.dtos.UpdateEventAdminRequest;
import ru.practicum.models.events.model.dtos.UpdateGroupEventsAdminRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminEventService {
    List<EventFullDto> searchEventForAdmin(List<Long> users, List<State> states, List<Long> categories,
                                           LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from,
                                           Integer size);

    ReviewEventFullDto reviewEvent(Long eventId, UpdateEventAdminRequest updateEvent);

    List<EventFullDto> eventsWaitingForAReview();

    List<ReviewEventFullDto> reviewingEvents(UpdateGroupEventsAdminRequest reviewGroup);
}
