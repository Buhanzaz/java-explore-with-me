package ru.practicum.events.service;

import ru.practicum.enums.Sort;
import ru.practicum.models.events.model.dtos.EventFullDto;
import ru.practicum.models.events.model.dtos.EventShortDto;

import java.time.LocalDateTime;
import java.util.List;

public interface PublicEventService {
    EventFullDto getEvent(Long id);

    List<EventShortDto> findEvents(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, Sort sort, Integer from, Integer size);
}
